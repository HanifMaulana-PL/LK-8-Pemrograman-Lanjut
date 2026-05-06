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
import java.awt.*;

public class JenisPanel extends JPanel {

    private DefaultTableModel tabelModel;
    private JTable            tabel;

    public JenisPanel() {
        setLayout(null);
        initUI();
        muatData();
    }

    private void initUI() {
        JLabel lblJudul = new JLabel("Data Jenis Buku");
        lblJudul.setBounds(10, 10, 200, 25);
        add(lblJudul);

        // Tabel 
        String[] kolom = {"Kode Jenis", "Nama Jenis"};
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

        btnTambah.setBounds(10,   490, 90, 30);
        btnEdit.setBounds(110,    490, 90, 30);
        btnHapus.setBounds(210,   490, 90, 30);
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
        for (JenisBuku j : JenisBuku.lihat()) {
            tabelModel.addRow(new Object[]{j.getKodeJenis(), j.getNamaJenis()});
        }
    }

    private void dialogTambah() {
        JTextField tfKode = new JTextField();
        JTextField tfNama = new JTextField();
        Object[] fields   = {"Kode Jenis:", tfKode, "Nama Jenis:", tfNama};

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Tambah Jenis Buku",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                JenisBuku.tambah(tfKode.getText().trim(), tfNama.getText().trim());
                muatData();
                JOptionPane.showMessageDialog(this, "Jenis buku berhasil ditambahkan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dialogEdit() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih jenis buku yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kode     = (String) tabelModel.getValueAt(baris, 0);
        String namaLama = (String) tabelModel.getValueAt(baris, 1);

        JTextField tfNama = new JTextField(namaLama);
        Object[] fields   = {"Kode (tidak bisa diubah): " + kode, new JSeparator(), "Nama Baru:", tfNama};

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Edit Jenis - " + kode,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                JenisBuku.edit(kode, tfNama.getText().trim());
                muatData();
                JOptionPane.showMessageDialog(this, "Jenis buku berhasil diperbarui!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesHapus() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih jenis buku yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kode = (String) tabelModel.getValueAt(baris, 0);
        String nama = (String) tabelModel.getValueAt(baris, 1);

        int ok = JOptionPane.showConfirmDialog(
            this,
            "Hapus jenis \"" + nama + "\" (" + kode + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (ok == JOptionPane.YES_OPTION) {
            try {
                JenisBuku.hapus(kode);
                muatData();
                JOptionPane.showMessageDialog(this, "Jenis buku berhasil dihapus.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
