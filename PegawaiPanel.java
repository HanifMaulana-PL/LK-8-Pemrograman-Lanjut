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


public class PegawaiPanel extends JPanel {

    private DefaultTableModel tabelModel;
    private JTable            tabel;

    public PegawaiPanel() {
        setLayout(null);
        initUI();
        muatData();
    }

    private void initUI() {
        JLabel lblJudul = new JLabel("Data Pegawai");
        lblJudul.setBounds(10, 10, 200, 25);
        add(lblJudul);

        // Tabel 
        String[] kolom = {"NIP", "Nama", "Tanggal Lahir"};
        tabelModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabel = new JTable(tabelModel);

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBounds(10, 45, 710, 430);
        add(scroll);

        // Tombol
        JButton btnTambah  = new JButton("Tambah");
        JButton btnEdit    = new JButton("Edit");
        JButton btnHapus   = new JButton("Hapus");
        JButton btnRefresh = new JButton("Refresh");

        btnTambah.setBounds(10,  490, 90, 30);
        btnEdit.setBounds(110,   490, 90, 30);
        btnHapus.setBounds(210,  490, 90, 30);
        btnRefresh.setBounds(310, 490, 90, 30);

        add(btnTambah);
        add(btnEdit);
        add(btnHapus);
        add(btnRefresh);

        btnTambah.addActionListener(e  -> dialogTambah());
        btnEdit.addActionListener(e    -> dialogEdit());
        btnHapus.addActionListener(e   -> prosesHapus());
        btnRefresh.addActionListener(e -> muatData());
    }

    private void muatData() {
        tabelModel.setRowCount(0);
        for (Pegawai p : Pegawai.lihat()) {
            tabelModel.addRow(new Object[]{
                p.getNIP(),
                p.getNama(),
                p.getTanggalLahir()
            });
        }
    }

    private void dialogTambah() {
        JTextField tfNIP = new JTextField();
        JTextField tfNama = new JTextField();
        JTextField tfTgl  = new JTextField();

        Object[] fields = {
            "NIP:",           tfNIP,
            "Nama:",          tfNama,
            "Tanggal Lahir:", tfTgl
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Tambah Pegawai",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                Pegawai.tambah(
                    tfNIP.getText().trim(),
                    tfNama.getText().trim(),
                    tfTgl.getText().trim()
                );
                muatData();
                JOptionPane.showMessageDialog(this, "Pegawai berhasil ditambahkan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dialogEdit() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih pegawai yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nip     = (String) tabelModel.getValueAt(baris, 0);
        String namaLama = (String) tabelModel.getValueAt(baris, 1);
        String tglLama  = (String) tabelModel.getValueAt(baris, 2);

        JTextField tfNama = new JTextField(namaLama);
        JTextField tfTgl  = new JTextField(tglLama);

        Object[] fields = {
            "NIP (tidak bisa diubah): " + nip, new JSeparator(),
            "Nama:",          tfNama,
            "Tanggal Lahir:", tfTgl
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Edit Pegawai - " + nip,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                Pegawai.edit(
                    nip,
                    tfNama.getText().trim(),
                    tfTgl.getText().trim()
                );
                muatData();
                JOptionPane.showMessageDialog(this, "Data pegawai berhasil diperbarui!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesHapus() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih pegawai yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nip  = (String) tabelModel.getValueAt(baris, 0);
        String nama = (String) tabelModel.getValueAt(baris, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus pegawai \"" + nama + "\" (" + nip + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Pegawai.hapus(nip);
                muatData();
                JOptionPane.showMessageDialog(this, "Pegawai berhasil dihapus.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
