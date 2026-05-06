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

public class LoginFrame extends JFrame {

    private JTextField     tfUsername;
    private JPasswordField pfPassword;
    private JButton        btnLogin;
    private JLabel         lblStatus;

    private Auth auth = new Auth();

    public LoginFrame() {
        setTitle("Login - Sistem Perpustakaan");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        initUI();
    }

    private void initUI() {
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(40, 30, 80, 25);
        add(lblUsername);

        tfUsername = new JTextField();
        tfUsername.setBounds(130, 30, 160, 25);
        add(tfUsername);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(40, 70, 80, 25);
        add(lblPassword);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(130, 70, 160, 25);
        add(pfPassword);

        lblStatus = new JLabel("");
        lblStatus.setBounds(40, 105, 260, 20);
        lblStatus.setForeground(Color.RED);
        add(lblStatus);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(120, 135, 100, 30);
        add(btnLogin);

        JButton btnRegis = new JButton("Register");
        btnRegis.setBounds(120, 175, 100, 30);
        add(btnRegis);
        
        btnRegis.addActionListener(e -> dialogRegister());
        btnLogin.addActionListener(e -> prosesLogin());
        pfPassword.addActionListener(e -> prosesLogin());
        tfUsername.addActionListener(e -> pfPassword.requestFocus());
    }

    private void dialogRegister() {
    JTextField tfUser  = new JTextField();
    JTextField tfPass  = new JPasswordField();
    String[] pilihanRole = {"default", "admin"};
    JComboBox<String> cbRole = new JComboBox<>(pilihanRole);

    Object[] fields = {
        "Username:", tfUser,
        "Password:", tfPass,
        "Role:",     cbRole
    };

    int opt = JOptionPane.showConfirmDialog(
        this, fields, "Register Akun Baru",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (opt == JOptionPane.OK_OPTION) {
        try {
            Auth.daftar(
                tfUser.getText().trim(),
                tfPass.getText().trim(),
                (String) cbRole.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, "Akun berhasil dibuat! Silakan login.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void prosesLogin() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            lblStatus.setText("Username dan password wajib diisi!");
            return;
        }

        boolean berhasil = auth.authenticate(username, password);

        if (berhasil) {
            new MainFrame(auth).setVisible(true);
            dispose();
        } else {
            lblStatus.setText("Username atau password salah!");
            pfPassword.setText("");
            pfPassword.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
