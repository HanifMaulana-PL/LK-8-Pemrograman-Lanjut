/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk8pemlan;

/**
 *
 * @author Hanif Maulana
 */
public class Auth {

    private static final String FILE = "akun.txt";
    // Kredensial hardcoded 
    private final String defaultUser = "Hanif";
    private final String adminUser = "admin";
    private final String passwordDefault = "123";
    private final String passwordAdmin = "123";

    private boolean isLoggedIn = false;
    private String roleSaatIni = "";

    public boolean authenticate(String username, String password) {
        
        if (username.equals(defaultUser) && password.equals(passwordDefault)) {
        isLoggedIn  = true;
        roleSaatIni = "default";
        return true;
    }
    if (username.equals(adminUser) && password.equals(passwordAdmin)) {
        isLoggedIn  = true;
        roleSaatIni = "admin";
        return true;
    }

        FileHandler file = new FileHandler(FILE);
        for (String baris : file.baca()) {
            String[] p = baris.split("\\|");
            if (p.length == 3) {
                if (p[0].equals(username) && p[1].equals(password)) {
                    isLoggedIn = true;
                    roleSaatIni = p[2];
                    return true;
                }
            } else if (username.equals(adminUser)) {
                if (password.equals(passwordAdmin)) {
                    isLoggedIn = true;
                    roleSaatIni = "admin";
                    return true;
                }
            }
        }
        return false;
    }

    public static void daftar(String username, String password, String role) throws Exception{
       if (username.isEmpty() || password.isEmpty())
            throw new Exception("Username dan password tidak boleh kosong!");

        FileHandler file = new FileHandler(FILE);
        for (String baris : file.baca()) {
            String[] p = baris.split("//|");
            if (p.length == 3 && p[0].equals(username))
                throw new Exception("Username '" + username + "' sudah dipakai!");
        } 
        
        file.tulis(username + "|" + password + "|" + role);
    }
    
    public boolean isLoggedIn() { return isLoggedIn; }
    public String  getRole()    { return roleSaatIni; }
    
    // Logout 
    public void logout() {
        isLoggedIn = false;
        roleSaatIni = "";
    }
}
