/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk8pemlan;

/**
 *
 * @author Hanif Maulana
 */
import java.io.*;
import java.util.*;
 
public class FileHandler {
 
    private String filePath;
 
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }
 
    // Baca file
    public List<String> baca() {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            // File belum ada
        }
        return data;
    }
 
    // Tulis ke file
    public void tulis(String text) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(text);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error menulis file: " + e.getMessage());
        }
    }
 
    // Hapus semua isi file
    public void hapusData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("");
        } catch (IOException e) {
            System.out.println("Error menghapus data: " + e.getMessage());
        }
    }
}

