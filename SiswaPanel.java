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


public class SiswaPanel extends JPanel {

    private DefaultTableModel tabelModel;
    private JTable            tabel;

    public SiswaPanel() {
        setLayout(null);
        initUI();
        muatData();
    }

    private void initUI() {
        JLabel lblJudul = new JLabel("Data Siswa");
        lblJudul.setBounds(10, 10, 200, 25);
        add(lblJudul);

        // Tabel 
        String[] kolom = {"NIS", "Nama", "Alamat"};
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
        for (Siswa s : Siswa.lihat()) {
            tabelModel.addRow(new Object[]{
                s.getNIS(),
                s.getNama(),
                s.getAlamat()
            });
        }
    }

    private void dialogTambah() {
        JTextField tfNIS    = new JTextField();
        JTextField tfNama   = new JTextField();
        JTextField tfAlamat = new JTextField();

        Object[] fields = {
            "NIS:",    tfNIS,
            "Nama:",   tfNama,
            "Alamat:", tfAlamat
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Tambah Siswa",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                Siswa.tambah(
                    tfNIS.getText().trim(),
                    tfNama.getText().trim(),
                    tfAlamat.getText().trim()
                );
                muatData();
                JOptionPane.showMessageDialog(this, "Siswa berhasil ditambahkan!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dialogEdit() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih siswa yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nis       = (String) tabelModel.getValueAt(baris, 0);
        String namaLama  = (String) tabelModel.getValueAt(baris, 1);
        String alamatLama = (String) tabelModel.getValueAt(baris, 2);

        JTextField tfNama   = new JTextField(namaLama);
        JTextField tfAlamat = new JTextField(alamatLama);

        Object[] fields = {
            "NIS (tidak bisa diubah): " + nis, new JSeparator(),
            "Nama:",   tfNama,
            "Alamat:", tfAlamat
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Edit Siswa - " + nis,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                Siswa.edit(
                    nis,
                    tfNama.getText().trim(),
                    tfAlamat.getText().trim()
                );
                muatData();
                JOptionPane.showMessageDialog(this, "Data siswa berhasil diperbarui!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesHapus() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih siswa yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nis  = (String) tabelModel.getValueAt(baris, 0);
        String nama = (String) tabelModel.getValueAt(baris, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus siswa \"" + nama + "\" (" + nis + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Siswa.hapus(nis);
                muatData();
                JOptionPane.showMessageDialog(this, "Siswa berhasil dihapus.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}