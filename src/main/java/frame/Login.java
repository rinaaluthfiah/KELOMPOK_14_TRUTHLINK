/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */

public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());
    private String idUserLogin;

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        Username = new javax.swing.JTextField();
        Password = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        btnLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Login (1).png"))); // NOI18N
        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 533));
        setResizable(false);
        getContentPane().setLayout(null);

        Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameActionPerformed(evt);
            }
        });
        getContentPane().add(Username);
        Username.setBounds(180, 290, 390, 30);

        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });
        getContentPane().add(Password);
        Password.setBounds(180, 340, 390, 30);

        jButton2.setBackground(new java.awt.Color(24, 42, 66));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("<  Kembali");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(10, 10, 70, 20);

        btnLogin.setBackground(new java.awt.Color(24, 55, 106));
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        getContentPane().add(btnLogin);
        btnLogin.setBounds(180, 397, 390, 30);

        jLabel4.setText("username");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(180, 270, 70, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tidak memiliki akun?");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(270, 434, 110, 30);

        jLabel6.setText("password");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(180, 320, 60, 20);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/truthlink_3 1.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(300, 150));
        jLabel3.setMinimumSize(new java.awt.Dimension(300, 150));
        jPanel1.add(jLabel3);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(300, 90, 140, 140);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Registrasi disini");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(380, 430, 90, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Login (1).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 750, 530);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void UsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameActionPerformed

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Home homePage = new Home();
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
    String usernameInput = Username.getText();
    String passwordInput = new String(Password.getPassword());

    if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Username dan password tidak boleh kosong!", 
            "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    org.hibernate.Session session = null;
    try {
        session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession();

        entity.User user = session.createQuery(
                "FROM User WHERE username = :username", entity.User.class)
                .setParameter("username", usernameInput)
                .uniqueResult();

        if (user == null) {
            JOptionPane.showMessageDialog(this, 
                "Username tidak ditemukan!", 
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!user.getPassword().equals(passwordInput)) {
            JOptionPane.showMessageDialog(this, 
                "Password salah!", 
                "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        entity.Admin admin = session.createQuery(
                "FROM Admin WHERE id_user = :id", entity.Admin.class)
                .setParameter("id", user.getId_user())
                .uniqueResult();

        if (admin != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil sebagai Admin!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            new frame.BerandaAdmin(admin).setVisible(true);
            this.dispose();
            return;
        }

        entity.Pelapor pelapor = session.createQuery(
                "FROM Pelapor WHERE id_user = :id", entity.Pelapor.class)
                .setParameter("id", user.getId_user())
                .uniqueResult();

        if (pelapor != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil sebagai Pelapor!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            frame.MainUser mainUser = new frame.MainUser(user.getId_user());
            mainUser.setVisible(true);
            this.dispose();
            return;
        }

        JOptionPane.showMessageDialog(this, "Jenis akun tidak dikenali!", "Peringatan", JOptionPane.WARNING_MESSAGE);

    } catch (org.hibernate.exception.JDBCConnectionException ex) {
        logger.log(java.util.logging.Level.SEVERE, "Koneksi ke database gagal", ex);
        JOptionPane.showMessageDialog(this, 
                "Tidak dapat terhubung ke database.\nPeriksa koneksi server.", 
                "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);

    } catch (org.hibernate.HibernateException ex) {
        logger.log(java.util.logging.Level.SEVERE, "Kesalahan Sistem", ex);
        JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan sistem database. Silakan coba lagi.", 
                "Kesalahan Database", JOptionPane.ERROR_MESSAGE);

    } catch (Exception ex) {
        logger.log(java.util.logging.Level.SEVERE, "Kesalahan pada proses login", ex);
        JOptionPane.showMessageDialog(this, 
                "Terjadi kesalahan tak terduga: " + ex.getMessage(), 
                "Kesalahan Sistem", JOptionPane.ERROR_MESSAGE);

    } finally {
        if (session != null) session.close();
    }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        Register registerPage = new Register();
        registerPage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Password;
    private javax.swing.JTextField Username;
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
