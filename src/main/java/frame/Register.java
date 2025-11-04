/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;
import javax.swing.JOptionPane;
import entity.Pelapor;
import Dao.PelaporIml;
import Dao.PelaporDao;
import java.util.Date;



/**
 *
 * @author ASUS
 */
public class Register extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Register.class.getName());
    private javax.swing.ButtonGroup genderGroup;
    
    
    public Register() {
        initComponents();
        genderGroup = new javax.swing.ButtonGroup();
        genderGroup.add(jRadioButton1);
        genderGroup.add(jRadioButton2);
        ((javax.swing.JTextField) dateTanggalLahir.getDateEditor().getUiComponent()).setEditable(false);


        dateTanggalLahir.getDateEditor().getUiComponent().addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) { dateTanggalLahir.getCalendarButton().doClick(); }
        });
        dateTanggalLahir.getDateEditor().addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) dateTanggalLahir.getCalendarButton().doClick();
        });
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        txtKonfirmasiPassword = new javax.swing.JPasswordField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        dateTanggalLahir = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(740, 550));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel2.setText("Tanggal lahir (yyyy-mm-dd)");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(460, 170, 140, 14);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel3.setText("Alamat Email");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(70, 170, 80, 20);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel4.setText("Username");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(70, 220, 80, 20);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setText("Password");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(70, 280, 80, 14);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setText("Konfirmasi password");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(70, 330, 100, 20);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel7.setText("Jenis Kelamin");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(460, 120, 80, 20);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel8.setText("Sudah memiliki akun?");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(280, 460, 110, 20);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel9.setText("Nama Lengkap");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(70, 120, 80, 14);

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        getContentPane().add(txtNama);
        txtNama.setBounds(70, 136, 360, 30);

        txtEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmailMouseClicked(evt);
            }
        });
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        getContentPane().add(txtEmail);
        txtEmail.setBounds(70, 190, 360, 30);
        getContentPane().add(txtUsername);
        txtUsername.setBounds(70, 240, 360, 30);

        jButton4.setBackground(new java.awt.Color(24, 55, 106));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Register");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(180, 420, 380, 30);

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtPassword);
        txtPassword.setBounds(70, 296, 360, 30);

        txtKonfirmasiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKonfirmasiPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(txtKonfirmasiPassword);
        txtKonfirmasiPassword.setBounds(70, 350, 360, 30);

        jRadioButton1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRadioButton1.setText("Laki-laki");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton1);
        jRadioButton1.setBounds(460, 140, 80, 20);

        jRadioButton2.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRadioButton2.setText("Perempuan");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton2);
        jRadioButton2.setBounds(550, 140, 80, 20);

        jButton1.setBackground(new java.awt.Color(24, 55, 106));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText(" <  Kembali");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(10, 10, 80, 20);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Masuk Disini");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel10);
        jLabel10.setBounds(400, 460, 80, 20);

        dateTanggalLahir.setDateFormatString("yyyy-MM-dd");
        getContentPane().add(dateTanggalLahir);
        dateTanggalLahir.setBounds(460, 190, 200, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Regist (1).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(-10, 0, 750, 534);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Home homePage = new Home();
        homePage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    String nama = txtNama.getText().trim();
    String email = txtEmail.getText().trim();
    String username = txtUsername.getText().trim();
    String password = new String(txtPassword.getPassword());
    String confirmPassword = new String(txtKonfirmasiPassword.getPassword());
    String jenisKelamin = jRadioButton1.isSelected() ? "Pria" :
                          jRadioButton2.isSelected() ? "Wanita" : null;
    Date tanggalLahir = dateTanggalLahir.getDate();
    
    if (nama.isEmpty() || email.isEmpty() || username.isEmpty() ||
        password.isEmpty() || confirmPassword.isEmpty() || jenisKelamin == null || tanggalLahir == null) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (nama.length() > 50 || email.length() > 50 || username.length() > 30) {
        JOptionPane.showMessageDialog(this, "Input terlalu panjang! Periksa batas karakter.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (tanggalLahir == null) {
        JOptionPane.showMessageDialog(this, "Tanggal lahir harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (tanggalLahir.after(new java.util.Date())) {
        JOptionPane.showMessageDialog(this, "Tanggal lahir tidak boleh setelah hari ini!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.com$";
    if (!email.matches(emailPattern)) {
        JOptionPane.showMessageDialog(this, 
            "Format email tidak valid! Harus mengandung '@' dan diakhiri dengan '.com'\nContoh: nama@email.com", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!password.equals(confirmPassword)) {
        JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak sama!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Pelapor pelapor = new Pelapor();
    pelapor.setNama(nama);
    pelapor.setEmail(email);
    pelapor.setUsername(username);
    pelapor.setPassword(password);
    pelapor.setTanggal_lahir(tanggalLahir);

    if (jRadioButton1.isSelected()) {
        pelapor.setJenis_kelamin(Pelapor.JenisKelamin.Pria);
    } else if (jRadioButton2.isSelected()) {
        pelapor.setJenis_kelamin(Pelapor.JenisKelamin.Wanita);
    }

        try {
            PelaporDao pelaporDao = new PelaporIml();
            pelaporDao.save(pelapor);

            JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            new Login().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void txtKonfirmasiPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKonfirmasiPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKonfirmasiPasswordActionPerformed

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        Login loginPage = new Login();
        loginPage.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void txtEmailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmailMouseClicked

    }//GEN-LAST:event_txtEmailMouseClicked

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Register().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser dateTanggalLahir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JPasswordField txtKonfirmasiPassword;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
