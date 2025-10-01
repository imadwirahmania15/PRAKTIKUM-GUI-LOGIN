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

public class RegisForm extends javax.swing.JFrame {
     // Komponen GUI
    private JTextField txtUsername;
    private JTextField txtNamaLengkap;
    private JTextField txtEmail;
    private JTextArea txtAlamat;
    private JButton btnSave;
    private JButton btnClose;
    private Connection conn; // koneksi database
    
    public RegisForm() {
        setTitle("Form Registrasi");
        setSize(500, 400); // lebih besar
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    // Gunakan GridBagLayout biar rapi
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 250)); // kasih warna bg
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // spasi antar komponen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        //  Nama Lengkap
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Nama Lengkap:"), gbc);
        gbc.gridx = 1;
        txtNamaLengkap = new JTextField(20);
        panel.add(txtNamaLengkap, gbc);

        //Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        //  Alamat
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextArea(4, 20);
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);
        panel.add(scrollAlamat, gbc);
        
         // Tombol Save
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST; // pojok kanan bawah
        btnSave = new JButton("Save");
        panel.add(btnSave, gbc);


        // Tombol Close
        gbc.gridx = 2; gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST; 
        btnClose = new JButton("Close");
        panel.add(btnClose, gbc);

        add(panel);
        
        // Inisialisasi koneksi DB
        connectDB();
        
        
        // Aksi tombol save
        btnSave.addActionListener(
                e -> saveAction());
        // Aksi tombol close
        btnClose.addActionListener(
                e -> dispose());
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
    

   // Fungsi simpan data
    private void saveAction() {
        String username = txtUsername.getText();
        String namaLengkap = txtNamaLengkap.getText();
        String email = txtEmail.getText();
        String alamat = txtAlamat.getText();

        if (username.isEmpty() || namaLengkap.isEmpty() || email.isEmpty() || alamat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        try {
            String sql = "INSERT INTO users (username, password, nama_lengkap, email) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, "123456"); // password default
            pstmt.setString(3, namaLengkap);
            pstmt.setString(4, email);

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Reset field
    private void resetForm() {
        txtUsername.setText("");
        txtNamaLengkap.setText("");
        txtEmail.setText("");
        txtAlamat.setText("");
        txtUsername.requestFocus();
    }

    
    /**
     * Creates new form RegisForm
     */
   
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
            java.util.logging.Logger.getLogger(RegisForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegisForm().setVisible(true);
            }
        });
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

