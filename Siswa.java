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

public class Siswa {

    private String NIS;
    private String nama;
    private String alamat;

    private static final String FILE = "siswa.txt";

    public Siswa() {}

    public Siswa(String NIS, String nama, String alamat) {
        this.NIS    = NIS;
        this.nama   = nama;
        this.alamat = alamat;
    }

    // Getter 
    public String getNIS()    { return NIS;    }
    public String getNama()   { return nama;   }
    public String getAlamat() { return alamat; }

    // Setter 
    public void setNama(String nama)     { this.nama   = nama;   }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    // CRUD 

    // Baca semua data siswa dari file. 
    public static List<Siswa> lihat() {
        List<Siswa> list = new ArrayList<>();
        FileHandler file = new FileHandler(FILE);
        for (String b : file.baca()) {
            String[] p = b.split("\\|");
            if (p.length == 3)
                list.add(new Siswa(p[0], p[1], p[2]));
        }
        return list;
    }

    // Simpan ulang semua data siswa ke file. 
    public static void simpanSemua(List<Siswa> list) {
        FileHandler file = new FileHandler(FILE);
        file.hapusData();
        for (Siswa s : list)
            file.tulis(s.NIS + "|" + s.nama + "|" + s.alamat);
    }
    
    public static void tambah(String nis, String nama, String alamat) throws Exception {
        if (nis.isEmpty() || nama.isEmpty())
            throw new Exception("NIS dan Nama tidak boleh kosong!");
        for (Siswa s : lihat())
            if (s.NIS.equalsIgnoreCase(nis))
                throw new Exception("NIS '" + nis + "' sudah ada!");

        FileHandler file = new FileHandler(FILE);
        file.tulis(nis + "|" + nama + "|" + alamat);
    }
    
    public static void edit(String nis, String namaBaru, String alamatBaru) throws Exception {
        List<Siswa> list = lihat();
        for (Siswa s : list) {
            if (s.NIS.equalsIgnoreCase(nis)) {
                if (!namaBaru.isEmpty())   s.nama   = namaBaru;
                if (!alamatBaru.isEmpty()) s.alamat = alamatBaru;
                simpanSemua(list);
                return;
            }
        }
        throw new Exception("NIS '" + nis + "' tidak ditemukan.");
    }
    
    public static void hapus(String nis) throws Exception {
        List<Siswa> list = lihat();
        boolean dihapus = list.removeIf(s -> s.NIS.equalsIgnoreCase(nis));
        if (!dihapus)
            throw new Exception("NIS '" + nis + "' tidak ditemukan.");
        simpanSemua(list);
    }

    // Cari siswa berdasarkan NIS. 
    public static Siswa cari(String nis) {
        for (Siswa s : lihat())
            if (s.NIS.equalsIgnoreCase(nis))
                return s;
        return null;
    }

    @Override
    public String toString() {
        return NIS + " - " + nama;
    }
}
