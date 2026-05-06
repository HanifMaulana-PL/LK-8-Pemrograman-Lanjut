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
import java.util.List;
 
public class LaporanPanel extends JPanel {
 
    private DefaultTableModel modelSemua;
    private DefaultTableModel modelBelum;
    private DefaultTableModel modelTerlambat;
 
    public LaporanPanel() {
        setLayout(null);
        initUI();
        muatData();
    }
 
    private void initUI() {
        JLabel lblJudul = new JLabel("Laporan Peminjaman");
        lblJudul.setBounds(10, 10, 300, 25);
        add(lblJudul);
 
        // Tab
        JTabbedPane tab = new JTabbedPane();
        tab.setBounds(10, 45, 710, 440);
        add(tab);
 
        String[] kolom = {"Kode Trans", "NIS", "Kode Buku", "Tgl Pinjam", "Tgl Kembali", "Status"};
 
        // Tab 1
        modelSemua = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelSemua = new JTable(modelSemua);
        tab.addTab("Semua Riwayat", new JScrollPane(tabelSemua));
 
        // Tab 2
        modelBelum = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelBelum = new JTable(modelBelum);
        tab.addTab("Belum Dikembalikan", new JScrollPane(tabelBelum));
 
        // Tab 3
        modelTerlambat = new DefaultTableModel(kolom, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabelTerlambat = new JTable(modelTerlambat);
        tab.addTab("Terlambat", new JScrollPane(tabelTerlambat));
 
        // Tombol Refresh
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(10, 495, 90, 30);
        add(btnRefresh);
 
        btnRefresh.addActionListener(e -> muatData());
    }
 
    private void muatData() {
        isiTabel(modelSemua,     Laporan.semuaPeminjaman());
        isiTabel(modelBelum,     Laporan.belumDikembalikan());
        isiTabel(modelTerlambat, Laporan.terlambat());
    }
 
    private void isiTabel(DefaultTableModel model, List<Peminjaman> list) {
        model.setRowCount(0);
        for (Peminjaman pm : list) {
            String statusTeks = pm.getStatus() == 1 ? "Sudah" : "Belum";
            model.addRow(new Object[]{
                pm.getKodeTrans(),
                pm.getNis(),
                pm.getKodeBuku(),
                pm.getTglPinjam(),
                pm.getTglKembali(),
                statusTeks
            });
        }
    }
}
