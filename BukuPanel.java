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
import java.util.List;

public class BukuPanel extends JPanel {

    private DefaultTableModel tabelModel;
    private JTable            tabel;

    public BukuPanel() {
        setLayout(null);
        initUI();
        muatData();
    }

    private void initUI() {
        JLabel lblJudul = new JLabel("Data Buku");
        lblJudul.setBounds(10, 10, 200, 25);
        add(lblJudul);

        // Tabel
        String[] kolom = {"Kode", "Judul", "Jenis", "Stok"};
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
        for (Buku b : Buku.lihat()) {
            tabelModel.addRow(new Object[]{
                b.getKodeBuku(),
                b.getJudul(),
                b.getJenisBuku().getNamaJenis(),
                b.getStok()
            });
        }
    }

    private void dialogTambah() {
        JTextField tfKode  = new JTextField();
        JTextField tfJudul = new JTextField();
        JTextField tfStok  = new JTextField("0");

        List<JenisBuku> jenisList = JenisBuku.lihat();
        JComboBox<JenisBuku> cbJenis = new JComboBox<>(jenisList.toArray(new JenisBuku[0]));

        Object[] fields = {
            "Kode Buku:",  tfKode,
            "Judul:",      tfJudul,
            "Jenis Buku:", cbJenis,
            "Stok:",       tfStok
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Tambah Buku",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                String kode  = tfKode.getText().trim();
                String judul = tfJudul.getText().trim();
                int    stok  = Integer.parseInt(tfStok.getText().trim());
                JenisBuku jenis = (JenisBuku) cbJenis.getSelectedItem();
                if (jenis == null) throw new Exception("Pilih jenis buku!");

                Buku.tambah(kode, judul, jenis.getKodeJenis(), stok);
                muatData();
                JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void dialogEdit() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kode      = (String) tabelModel.getValueAt(baris, 0);
        String judulLama = (String) tabelModel.getValueAt(baris, 1);
        int    stokLama  = (int)    tabelModel.getValueAt(baris, 3);

        JTextField tfJudul = new JTextField(judulLama);
        JTextField tfStok  = new JTextField(String.valueOf(stokLama));

        List<JenisBuku> jenisList = JenisBuku.lihat();
        JComboBox<JenisBuku> cbJenis = new JComboBox<>(jenisList.toArray(new JenisBuku[0]));

        String namaJenisLama = (String) tabelModel.getValueAt(baris, 2);
        for (int i = 0; i < jenisList.size(); i++) {
            if (jenisList.get(i).getNamaJenis().equals(namaJenisLama)) {
                cbJenis.setSelectedIndex(i);
                break;
            }
        }

        Object[] fields = {
            "Kode (tidak bisa diubah): " + kode, new JSeparator(),
            "Judul:",      tfJudul,
            "Jenis Buku:", cbJenis,
            "Stok:",       tfStok
        };

        int opt = JOptionPane.showConfirmDialog(
            this, fields, "Edit Buku - " + kode,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (opt == JOptionPane.OK_OPTION) {
            try {
                String judulBaru = tfJudul.getText().trim();
                int    stokBaru  = Integer.parseInt(tfStok.getText().trim());
                JenisBuku jenis  = (JenisBuku) cbJenis.getSelectedItem();
                if (jenis == null) throw new Exception("Pilih jenis buku!");

                Buku.edit(kode, judulBaru, jenis.getKodeJenis(), stokBaru);
                muatData();
                JOptionPane.showMessageDialog(this, "Data buku berhasil diperbarui!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void prosesHapus() {
        int baris = tabel.getSelectedRow();
        if (baris < 0) {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String kode  = (String) tabelModel.getValueAt(baris, 0);
        String judul = (String) tabelModel.getValueAt(baris, 1);

        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Hapus buku \"" + judul + "\" (" + kode + ")?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Buku.hapus(kode);
                muatData();
                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
