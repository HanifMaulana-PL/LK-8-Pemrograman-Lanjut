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

public class Pegawai {

    private String NIP;
    private String nama;
    private String tanggalLahir;

    private static final String FILE = "pegawai.txt";

    public Pegawai() {}

    public Pegawai(String NIP, String nama, String tanggalLahir) {
        this.NIP          = NIP;
        this.nama         = nama;
        this.tanggalLahir = tanggalLahir;
    }

    // Getter 
    public String getNIP()          { return NIP;          }
    public String getNama()         { return nama;         }
    public String getTanggalLahir() { return tanggalLahir; }

    // Setter 
    public void setNama(String nama)                 { this.nama         = nama;         }
    public void setTanggalLahir(String tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    // CRUD

    // Baca semua data pegawai dari file. 
    public static List<Pegawai> lihat() {
        List<Pegawai> list = new ArrayList<>();
        FileHandler file = new FileHandler(FILE);
        for (String b : file.baca()) {
            String[] p = b.split("\\|");
            if (p.length == 3)
                list.add(new Pegawai(p[0], p[1], p[2]));
        }
        return list;
    }

    // Simpan ulang semua data pegawai ke file. 
    public static void simpanSemua(List<Pegawai> list) {
        FileHandler file = new FileHandler(FILE);
        file.hapusData();
        for (Pegawai p : list)
            file.tulis(p.NIP + "|" + p.nama + "|" + p.tanggalLahir);
    }
    
    public static void tambah(String nip, String nama, String tanggalLahir) throws Exception {
        if (nip.isEmpty() || nama.isEmpty())
            throw new Exception("NIP dan Nama tidak boleh kosong!");
        for (Pegawai p : lihat())
            if (p.NIP.equalsIgnoreCase(nip))
                throw new Exception("NIP '" + nip + "' sudah ada!");

        FileHandler file = new FileHandler(FILE);
        file.tulis(nip + "|" + nama + "|" + tanggalLahir);
    }
    
    public static void edit(String nip, String namaBaru, String tglBaru) throws Exception {
        List<Pegawai> list = lihat();
        for (Pegawai p : list) {
            if (p.NIP.equalsIgnoreCase(nip)) {
                if (!namaBaru.isEmpty()) p.nama         = namaBaru;
                if (!tglBaru.isEmpty())  p.tanggalLahir = tglBaru;
                simpanSemua(list);
                return;
            }
        }
        throw new Exception("NIP '" + nip + "' tidak ditemukan.");
    }
    
    public static void hapus(String nip) throws Exception {
        List<Pegawai> list = lihat();
        boolean dihapus = list.removeIf(p -> p.NIP.equalsIgnoreCase(nip));
        if (!dihapus)
            throw new Exception("NIP '" + nip + "' tidak ditemukan.");
        simpanSemua(list);
    }

    // Cari pegawai berdasarkan NIP. 
    public static Pegawai cari(String nip) {
        for (Pegawai p : lihat())
            if (p.NIP.equalsIgnoreCase(nip))
                return p;
        return null;
    }

    @Override
    public String toString() {
        return NIP + " - " + nama;
    }
}
