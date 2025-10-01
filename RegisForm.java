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
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class RegisForm extends JFrame {
    private JTextField txtUsername, txtNamaLengkap, txtEmail;
    private JTextArea txtAlamat;
    private JButton btnSave, btnUpdate, btnDelete, btnClose;
    private JTable table;
    private DefaultTableModel model;
    private Connection conn;
    private int selectedId = -1; // id_profile

    public RegisForm() {
        setTitle("Form Registrasi");
        setSize(800, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(220, 235, 245));
        add(panel);

        // === Bagian Input (di atas tabel) ===
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(220, 235, 245)); // warna bg
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        formPanel.add(txtUsername, gbc);

        // Nama Lengkap
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Lengkap:"), gbc);
        gbc.gridx = 1;
        txtNamaLengkap = new JTextField(20);
        formPanel.add(txtNamaLengkap, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        formPanel.add(txtEmail, gbc);

        // Alamat
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextArea(4, 20);
        formPanel.add(new JScrollPane(txtAlamat), gbc);

        // Tombol CRUD
        gbc.gridx = 1; gbc.gridy = 4;
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(220, 235, 245)); // warna bg
        btnSave = new JButton("Save");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClose = new JButton("Close");
        btnPanel.add(btnSave);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClose);
        formPanel.add(btnPanel, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // === Bagian Tabel (di bawah input) ===
        model = new DefaultTableModel(new String[]{"ID Profile", "Username", "Nama Lengkap", "Email", "Alamat"}, 0);
        table = new JTable(model);
        table.setRowHeight(25);

        // warna tabel
        table.setBackground(new Color(245, 250, 255));
        table.setGridColor(new Color(200, 220, 240));
        table.setSelectionBackground(new Color(180, 210, 230));

        // set lebar kolom
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        table.getColumnModel().getColumn(4).setPreferredWidth(220);

        // Header center + bold
        ((javax.swing.table.DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.CENTER);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Event tombol
        btnSave.addActionListener(e -> saveAction());
        btnUpdate.addActionListener(e -> updateAction());
        btnDelete.addActionListener(e -> deleteAction());
        btnClose.addActionListener(e -> dispose());

        // Klik tabel isi ke field
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                txtUsername.setText(model.getValueAt(row, 1).toString());
                txtNamaLengkap.setText(model.getValueAt(row, 2).toString());
                txtEmail.setText(model.getValueAt(row, 3).toString());
                txtAlamat.setText(model.getValueAt(row, 4).toString());
            }
        });

        loadData();
    }

    // === koneksi database ===
    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/PBO";
        String user = "postgres";
        String password = "12345678";
        return DriverManager.getConnection(url, user, password);
    }

    // === load data ===
    private void loadData() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT p.id_profile, u.username, p.nama_lengkap, p.email, p.alamat " +
                         "FROM users u JOIN profile p ON u.id = p.id_user";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_profile"),
                        rs.getString("username"),
                        rs.getString("nama_lengkap"),
                        rs.getString("email"),
                        rs.getString("alamat")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }

    // === save data ===
    private void saveAction() {
        try (Connection conn = getConnection()) {
            String sqlUser = "INSERT INTO users (username, password) VALUES (?, ?) RETURNING id";
            PreparedStatement psUser = conn.prepareStatement(sqlUser);
            psUser.setString(1, txtUsername.getText());
            psUser.setString(2, "12345");
            ResultSet rs = psUser.executeQuery();
            rs.next();
            int userId = rs.getInt("id");

            String sqlProfile = "INSERT INTO profile (id_user, nama_lengkap, email, alamat) VALUES (?, ?, ?, ?)";
            PreparedStatement psProfile = conn.prepareStatement(sqlProfile);
            psProfile.setInt(1, userId);
            psProfile.setString(2, txtNamaLengkap.getText());
            psProfile.setString(3, txtEmail.getText());
            psProfile.setString(4, txtAlamat.getText());
            psProfile.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            resetForm();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Save gagal: " + e.getMessage());
        }
    }

    private void updateAction() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu di tabel!");
            return;
        }
        try (Connection conn = getConnection()) {
            String sql = "UPDATE profile SET nama_lengkap=?, email=?, alamat=? WHERE id_profile=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtNamaLengkap.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtAlamat.getText());
            ps.setInt(4, selectedId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            resetForm();
            loadData();
            selectedId = -1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update gagal: " + e.getMessage());
        }
    }

    private void deleteAction() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu di tabel!");
            return;
        }
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM profile WHERE id_profile=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedId);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            resetForm();
            loadData();
            selectedId = -1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete gagal: " + e.getMessage());
        }
    }

    private void resetForm() {
        txtUsername.setText("");
        txtNamaLengkap.setText("");
        txtEmail.setText("");
        txtAlamat.setText("");
        selectedId = -1;
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

