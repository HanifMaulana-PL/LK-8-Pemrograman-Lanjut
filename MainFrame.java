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
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel     cardPanel;
    private Auth       auth;

    public MainFrame(Auth auth) {
        this.auth = auth;

        setTitle("Sistem Perpustakaan");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        initUI();
    }

    private void initUI() {
        // Sidebar 
        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 160, 600);
        sidebar.setBackground(Color.LIGHT_GRAY);
        add(sidebar);

        JLabel lblJudul = new JLabel("Perpustakaan", SwingConstants.CENTER);
        lblJudul.setBounds(0, 10, 160, 25);
        sidebar.add(lblJudul);

        JLabel lblRole = new JLabel("Role: " + auth.getRole(), SwingConstants.CENTER);
        lblRole.setBounds(0, 35, 160, 20);
        sidebar.add(lblRole);

        String[] namaMenu  = {"Beranda", "Data Buku", "Jenis Buku", "Data Siswa", "Pegawai", "Peminjaman", "Laporan"};
        String[] keyMenu   = {"beranda", "buku", "jenis", "siswa", "pegawai", "peminjaman", "laporan"};

        for (int i = 0; i < namaMenu.length; i++) {
            JButton btn = new JButton(namaMenu[i]);
            btn.setBounds(10, 70 + i * 45, 140, 35);
            final String key = keyMenu[i];
            btn.addActionListener(e -> tampilkanPanel(key));
            sidebar.add(btn);
        }

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(10, 555, 140, 35);
        btnLogout.addActionListener(e -> prosesLogout());
        sidebar.add(btnLogout);

        // Area konten 
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);
        cardPanel.setBounds(160, 0, 740, 600);
        add(cardPanel);

        cardPanel.add(buatBerandaPanel(),    "beranda");
        cardPanel.add(new BukuPanel(),      "buku");
        cardPanel.add(new JenisPanel(),     "jenis");
        cardPanel.add(new SiswaPanel(),     "siswa");
        cardPanel.add(new PegawaiPanel(),   "pegawai");
        cardPanel.add(new PeminjamanPanel(), "peminjaman");
        cardPanel.add(new LaporanPanel(),   "laporan");

        tampilkanPanel("beranda");
    }

    private JPanel buatBerandaPanel() {
        JPanel p = new JPanel();
        p.setLayout(null);

        JLabel lbl = new JLabel("Selamat datang, " + auth.getRole().toUpperCase(), SwingConstants.CENTER);
        lbl.setBounds(0, 100, 740, 30);
        p.add(lbl);

        JLabel lbl2 = new JLabel("Pilih menu di sebelah kiri untuk mulai.", SwingConstants.CENTER);
        lbl2.setBounds(0, 140, 740, 25);
        p.add(lbl2);

        return p;
    }

    public void tampilkanPanel(String key) {
        cardLayout.show(cardPanel, key);
    }

    private void prosesLogout() {
        int konfirmasi = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin logout?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (konfirmasi == JOptionPane.YES_OPTION) {
            auth.logout();
            new LoginFrame().setVisible(true);
            dispose();
        }
    }
}
