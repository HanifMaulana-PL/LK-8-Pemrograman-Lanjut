/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk8pemlan;

/**
 *
 * @author Hanif Maulana
 */

import java.util.*;

public class Peminjaman {

    private String kodeTrans;
    private String nis;
    private String kodeBuku;   // bisa berisi beberapa kode dipisah koma
    private String tglPinjam;
    private String tglKembali;
    private int    status;     // 0 = belum kembali, 1 = sudah kembali

    private static final String FILE = "peminjaman.txt";

    public Peminjaman() {}

    public Peminjaman(String kodeTrans, String nis, String kodeBuku,
                      String tglPinjam, String tglKembali, int status) {
        this.kodeTrans  = kodeTrans;
        this.nis        = nis;
        this.kodeBuku   = kodeBuku;
        this.tglPinjam  = tglPinjam;
        this.tglKembali = tglKembali;
        this.status     = status;
    }

    // Getter 
    public String getKodeTrans()  { return kodeTrans;  }
    public String getNis()        { return nis;        }
    public String getKodeBuku()   { return kodeBuku;   }
    public String getTglPinjam()  { return tglPinjam;  }
    public String getTglKembali() { return tglKembali; }
    public int    getStatus()     { return status;     }

    // CRUD 

    // Baca semua data peminjaman dari file. 
    public static List<Peminjaman> lihat() {
        List<Peminjaman> list = new ArrayList<>();
        FileHandler file = new FileHandler(FILE);
        for (String b : file.baca()) {
            String[] p = b.split("\\|");
            if (p.length == 6) {
                int status = 0;
                try { status = Integer.parseInt(p[5]); } catch (NumberFormatException e) {}
                list.add(new Peminjaman(p[0], p[1], p[2], p[3], p[4], status));
            }
        }
        return list;
    }

    // Simpan ulang semua data peminjaman ke file. 
    public static void simpanSemua(List<Peminjaman> list) {
        FileHandler file = new FileHandler(FILE);
        file.hapusData();
        for (Peminjaman pm : list)
            file.tulis(pm.kodeTrans + "|" + pm.nis + "|" + pm.kodeBuku
                     + "|" + pm.tglPinjam + "|" + pm.tglKembali + "|" + pm.status);
    }
    
    public static void tambah(String kodeTrans, String nis, String kodeBuku,
                               String tglPinjam, String tglKembali) throws Exception {
        if (kodeTrans.isEmpty() || nis.isEmpty() || kodeBuku.isEmpty())
            throw new Exception("Kode transaksi, NIS, dan kode buku tidak boleh kosong!");

        for (Peminjaman pm : lihat())
            if (pm.kodeTrans.equalsIgnoreCase(kodeTrans))
                throw new Exception("Kode transaksi '" + kodeTrans + "' sudah ada!");

        if (Siswa.cari(nis) == null)
            throw new Exception("NIS '" + nis + "' tidak ditemukan!");

        // Validasi setiap kode buku
        String[] kodeArr = kodeBuku.split(",");
        for (String kb : kodeArr) {
            kb = kb.trim();
            if (kb.isEmpty()) continue;
            Buku b = Buku.cari(kb);
            if (b == null)
                throw new Exception("Kode buku '" + kb + "' tidak ditemukan!");
            if (b.getStok() <= 0)
                throw new Exception("Stok buku '" + kb + "' habis!");
        }

        FileHandler file = new FileHandler(FILE);
        file.tulis(kodeTrans + "|" + nis + "|" + kodeBuku
                 + "|" + tglPinjam + "|" + tglKembali + "|0");
    }
    
    public static void kembalikan(String kodeTrans) throws Exception {
        List<Peminjaman> list = lihat();
        for (Peminjaman pm : list) {
            if (pm.kodeTrans.equalsIgnoreCase(kodeTrans)) {
                if (pm.status == 1)
                    throw new Exception("Buku sudah dikembalikan sebelumnya.");
                pm.status = 1;
                simpanSemua(list);
                return;
            }
        }
        throw new Exception("Kode transaksi '" + kodeTrans + "' tidak ditemukan.");
    }
    
    public static void hapus(String kodeTrans) throws Exception {
        List<Peminjaman> list = lihat();
        boolean dihapus = list.removeIf(pm -> pm.kodeTrans.equalsIgnoreCase(kodeTrans));
        if (!dihapus)
            throw new Exception("Kode transaksi '" + kodeTrans + "' tidak ditemukan.");
        simpanSemua(list);
    }

    // Cek keterlambataan 
    public static boolean terlambat(String tglKembali) {
        String hariIni = java.time.LocalDate.now().toString(); // format yyyy-MM-dd
        return hariIni.compareTo(tglKembali) > 0;
    }

    @Override
    public String toString() {
        return kodeTrans + " | " + nis + " | " + kodeBuku;
    }
}
