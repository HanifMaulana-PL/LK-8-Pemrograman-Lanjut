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

public class Laporan {
    
    public static List<Peminjaman> semuaPeminjaman() {
        return Peminjaman.lihat();
    }
    
    public static List<Peminjaman> belumDikembalikan() {
        List<Peminjaman> hasil = new ArrayList<>();
        for (Peminjaman pm : Peminjaman.lihat()) {
            if (pm.getStatus() == 0) {
                hasil.add(pm);
            }
        }
        return hasil;
    }
    
    public static List<Peminjaman> terlambat() {
        List<Peminjaman> hasil = new ArrayList<>();
        for (Peminjaman pm : Peminjaman.lihat()) {
            if (pm.getStatus() == 0 && Peminjaman.terlambat(pm.getTglKembali())) {
                hasil.add(pm);
            }
        }
        return hasil;
    }
}