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
public class JenisBuku {

    private String kodeJenis;
    private String namaJenis;

    private static final String FILE = "jenis_buku.txt";

    public JenisBuku() {}

    public JenisBuku(String kodeJenis, String namaJenis) {
        this.kodeJenis = kodeJenis;
        this.namaJenis = namaJenis;
    }

    // Getter
    public String getKodeJenis() { return kodeJenis; }
    public String getNamaJenis() { return namaJenis; }

    // Setter 
    public void setNamaJenis(String namaJenis) { this.namaJenis = namaJenis; }

    // CRUD

    // Baca semua data dari file. 
    public static List<JenisBuku> lihat() {
        List<JenisBuku> list = new ArrayList<>();
        FileHandler file = new FileHandler(FILE);
        for (String b : file.baca()) {
            String[] p = b.split("\\|");
            if (p.length == 2)
                list.add(new JenisBuku(p[0], p[1]));
        }
        return list;
    }

    // Simpan ulang semua data ke file. 
    public static void simpanSemua(List<JenisBuku> list) {
        FileHandler file = new FileHandler(FILE);
        file.hapusData();
        for (JenisBuku j : list)
            file.tulis(j.kodeJenis + "|" + j.namaJenis);
    }

    // Tambah jenis buku baru.
    public static void tambah(String kode, String nama) throws Exception {
        if (kode.isEmpty() || nama.isEmpty())
            throw new Exception("Kode dan Nama tidak boleh kosong!");
        for (JenisBuku j : lihat())
            if (j.kodeJenis.equalsIgnoreCase(kode))
                throw new Exception("Kode jenis '" + kode + "' sudah ada!");

        FileHandler file = new FileHandler(FILE);
        file.tulis(kode + "|" + nama);
    }
    
    public static void edit(String kode, String namaBaru) throws Exception {
        if (namaBaru.isEmpty())
            throw new Exception("Nama baru tidak boleh kosong!");
        List<JenisBuku> list = lihat();
        for (JenisBuku j : list) {
            if (j.kodeJenis.equalsIgnoreCase(kode)) {
                j.namaJenis = namaBaru;
                simpanSemua(list);
                return;
            }
        }
        throw new Exception("Kode jenis '" + kode + "' tidak ditemukan.");
    }
    
    public static void hapus(String kode) throws Exception {
        List<JenisBuku> list = lihat();
        boolean dihapus = list.removeIf(j -> j.kodeJenis.equalsIgnoreCase(kode));
        if (!dihapus)
            throw new Exception("Kode jenis '" + kode + "' tidak ditemukan.");
        simpanSemua(list);
    }

    // Cari jenis buku berdasarkan kode. 
    public static JenisBuku cari(String kode) {
        for (JenisBuku j : lihat())
            if (j.kodeJenis.equalsIgnoreCase(kode))
                return j;
        return null;
    }

    @Override
    public String toString() {
        return kodeJenis + " - " + namaJenis;
    }
}
