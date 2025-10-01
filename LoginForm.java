/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author imadw
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends javax.swing.JFrame {
    // Komponen GUI
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnReset;
    private JButton btnRegist;

    // Koneksi Database
    private Connection conn;

    public LoginForm() {
        setTitle("Login Form");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(220, 235, 245)); // kasih warna ke panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;  // biar label rata kiri
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Baris 1 - Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        txtUsername = new JTextField(15); // panjang field 15 karakter
        panel.add(txtUsername, gbc);

// Baris 2 - Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        txtPassword = new JPasswordField(15);
        panel.add(txtPassword, gbc);

// Baris 3 - Tombol di bawah
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // tombol menempati 2 kolom
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        panelButton.setBackground(new Color(220, 235, 245)); // tombol panel ikut warna
       
        btnLogin = new JButton("Login");
        btnReset = new JButton("Reset");
        panelButton.add(btnLogin);
        panelButton.add(btnReset);
        panel.add(panelButton, gbc);

        add(panel, BorderLayout.CENTER);

        // Inisialisasi koneksi DB
        connectDB();

        // Event tombol login
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
       
         
        // Event tombol reset
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetAction();
            }
        });
    }

    // Fungsi koneksi database
    private void connectDB() {
        try {
            String url = "jdbc:postgresql://localhost:5432/PBO";
            String user = "postgres"; // ganti dengan username PostgreSQL Anda
            String password = "12345678"; // ganti dengan password PostgreSQL Anda
            
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Koneksi berhasil.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi gagal: " + e.getMessage());
        }
    }

    // Fungsi login
    private void loginAction() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        try {
            String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang " + username);
           
                
             // Setelah login sukses, buka form Regis
            RegisForm regisForm = new RegisForm();
            regisForm.setVisible(true); 
            
             // Tutup form login
            this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
     // Fungsi reset
    private void resetAction() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus(); // biar kursor kembali ke username
    }

//    // Main
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new LoginForm().setVisible(true);
//        });
//    }
//}
//


    /**
     * Creates new form LoginForm
     */
//    public LoginForm() {
//        initComponents();
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
    
}

    /**
     *
     * @author imadw
     */
   // public static class Postgres {

        /**
         * @param args the command line arguments
         */
//        public static void main(String[] args) {
//            // TODO code application logic here
//        }
//    }
//}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

