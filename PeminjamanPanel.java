/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk8pemlan;

/**
 *
 * @author Hanif Maulana
 */

import javax.swing.*;
import javax.swing.table.*;

public class PeminjamanPanel extends JPanel {

    private DefaultTableModel tabelModel;
    private JTable            tabel;

    public PeminjamanPanel() {
        setLayout(null);
        initUI();
        muatData();
    }

    private void initUI() {
        JLabel lblJudul = new JLabel("Data Peminjaman");
        lblJudul.setBounds(10, 10, 200, 25);
        add(lblJudul);

        // Tabel 
        String[] kolom = {"Kode Trans", "NIS", "Kode Buku", "Tgl Pinjam", "Tgl Kembali", "Status"};
        tabelModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(tabelModel);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(10, 45, 710, 430);
        add(scroll);

        // Tombol
        JButton btnTambah    = new JButton("Tambah");
        JButton btnKembalikan = new JButton("Kembalikan");
        JButton btnHapus     = new JButton("Hapus");
        JButton btnRefresh   = new JButton("Refresh");

        btnTambah.setBounds(10,    490, 100, 30);
        btnKembalikan.setBounds(120, 490, 110, 30);
        btnHapus.setBounds(240,    490, 90,  30);
        btnRefresh.setBounds(340,  490, 90,  30);

        add(btnTambah);
        add(btnKembalikan);
        add(btnHapus);
        add(btnRefresh);

        btnTambah.addActionListener(e     -> dialogTambah());
        btnKembalikan.addActionListener(e -> prosesKembalikan());
        btnHapus.addActionListener(e      -> prosesHapus());
        btnRefresh.addActionListener(e    -> muatData());
    }

    private void muatData() {
        tabelModel.setRowCount(0);
        for (Peminjaman pm : Peminjaman.lihat()) {
            String statusTeks = pm.getStatus() == 1 ? "Sudah" : "Belum";
            tabelModel.addRow(new Object[]{
                pm.getKodeTrans(),
                pm.getNis(),
                pm.getKodeBuku(),
                pm.getTglPinjam(),
                pm.getTglKembali(),
                statusTeks
            });
        }
    }

    private void dialogTambah() {
        JTextField tfKodeTrans  = new JTextField();
        JTextField tfNIS        = new JTextField();
        JTextField tfKodeBuku   = new JTextField();
        JTextField tfTglPinjam  = new JTextField();
        JTextField tfTglKembali = new JTextField();

        JLabel lblInfo = new JLabel("Kode buku bisa lebih dari satu, pisah dengan koma. Contoh: B001,B002");
        lblInfo.setFont(new java.awt.Font("SansSerif", java.awt.Font.ITALIC, 11));

        Object[] fields = {
            "Kode Transaksi:", tfKodeTrans,
            "NIS Siswa:",      tfNIS,
            "Kode Buku:",      tfKodeBuku,
            lblInfo,
            "Tgl Pinjam (yyyy-MM-dd):",  tfTglPinjam,
            "Tgl Kembali (yyyy-MM-dd):", tfTglKembali
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Tambah Peminjaman",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                Peminjaman.tambah(
                    tfKodeTrans.getText().trim(),
                    tfNIS.getText().trim(),
                    tfKodeBuku.getText().trim(),
                    tfTglPinjam.getText().trim(),
                    tfTglKembali.getText().trim()
                );
                muatData();
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil ditambahkan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesKembalikan() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kodeTrans = (String) tabelModel.getValueAt(baris, 0);
        String nis       = (String) tabelModel.getValueAt(baris, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Kembalikan buku untuk transaksi " + kodeTrans + " (NIS: " + nis + ")?",
            "Konfirmasi Pengembalian",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Peminjaman.kembalikan(kodeTrans);
                muatData();
                JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesHapus() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kodeTrans = (String) tabelModel.getValueAt(baris, 0);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus data transaksi " + kodeTrans + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Peminjaman.hapus(kodeTrans);
                muatData();
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil dihapus.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
