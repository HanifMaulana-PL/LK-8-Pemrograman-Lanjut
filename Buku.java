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

public class Buku {

    private String    kodeBuku;
    private String    judul;
    private JenisBuku jenisBuku;
    private int       stok;

    private static final String FILE = "buku.txt";

    public Buku() {}

    public Buku(String kodeBuku, String judul, JenisBuku jenisBuku, int stok) {
        this.kodeBuku  = kodeBuku;
        this.judul     = judul;
        this.jenisBuku = jenisBuku;
        this.stok      = stok;
    }

    // Getter
    public String    getKodeBuku()  { return kodeBuku;  }
    public String    getJudul()     { return judul;     }
    public JenisBuku getJenisBuku() { return jenisBuku; }
    public int       getStok()      { return stok;      }

    // Setter 
    public void setJudul(String judul)         { this.judul     = judul;     }
    public void setStok(int stok)              { this.stok      = stok;      }
    public void setJenisBuku(JenisBuku jenis)  { this.jenisBuku = jenis;     }

    // CRUD

    // Baca semua data buku dari file. 
    public static List<Buku> lihat() {
        List<Buku> list = new ArrayList<>();
        FileHandler file = new FileHandler(FILE);
        for (String b : file.baca()) {
            String[] p = b.split("\\|");
            if (p.length == 4) {
                JenisBuku jenis = JenisBuku.cari(p[2]);
                if (jenis == null) jenis = new JenisBuku(p[2], "?");
                int stok = 0;
                try { stok = Integer.parseInt(p[3]); } catch (NumberFormatException e) {}
                list.add(new Buku(p[0], p[1], jenis, stok));
            }
        }
        return list;
    }

    // Simpan ulang semua data buku ke file. 
    public static void simpanSemua(List<Buku> list) {
        FileHandler file = new FileHandler(FILE);
        file.hapusData();
        for (Buku b : list)
            file.tulis(b.kodeBuku + "|" + b.judul + "|" + b.jenisBuku.getKodeJenis() + "|" + b.stok);
    }
    
    public static void tambah(String kode, String judul, String kodeJenis, int stok) throws Exception {
        if (kode.isEmpty() || judul.isEmpty())
            throw new Exception("Kode dan Judul tidak boleh kosong!");
        for (Buku b : lihat())
            if (b.kodeBuku.equalsIgnoreCase(kode))
                throw new Exception("Kode buku '" + kode + "' sudah ada!");
        JenisBuku jenis = JenisBuku.cari(kodeJenis);
        if (jenis == null)
            throw new Exception("Kode jenis '" + kodeJenis + "' tidak ditemukan!");
        if (stok < 0)
            throw new Exception("Stok tidak boleh negatif!");

        FileHandler file = new FileHandler(FILE);
        file.tulis(kode + "|" + judul + "|" + kodeJenis + "|" + stok);
    }
    
    public static void edit(String kode, String judulBaru, String kodeJenisBaru, int stokBaru) throws Exception {
        List<Buku> list = lihat();
        for (Buku b : list) {
            if (b.kodeBuku.equalsIgnoreCase(kode)) {
                if (!judulBaru.isEmpty())   b.judul = judulBaru;
                JenisBuku jenis = JenisBuku.cari(kodeJenisBaru);
                if (jenis != null)          b.jenisBuku = jenis;
                if (stokBaru >= 0)          b.stok = stokBaru;
                simpanSemua(list);
                return;
            }
        }
        throw new Exception("Kode buku '" + kode + "' tidak ditemukan.");
    }
    
    public static void hapus(String kode) throws Exception {
        List<Buku> list = lihat();
        boolean dihapus = list.removeIf(b -> b.kodeBuku.equalsIgnoreCase(kode));
        if (!dihapus)
            throw new Exception("Kode buku '" + kode + "' tidak ditemukan.");
        simpanSemua(list);
    }

    // Cari buku berdasarkan kode. 
    public static Buku cari(String kode) {
        for (Buku b : lihat())
            if (b.kodeBuku.equalsIgnoreCase(kode))
                return b;
        return null;
    }

    @Override
    public String toString() {
        return kodeBuku + " - " + judul;
    }
}
