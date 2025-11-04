package frame;


import com.mycompany.linkkk.HibernateUtil;
import entity.Laporan;
import entity.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BuatLaporan extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(BuatLaporan.class.getName());

    private String mode = "create";
    private String idLaporan;
    private ButtonGroup kriteriaGroup;
    private File selectedFile;
    private JLabel lblNamaFile;
    private String idUserLogin; 

    
    public BuatLaporan() {
        initComponents();
        setupButtonGroup();
        setupDragAndDrop();
    }

    public BuatLaporan(String idUserLogin) {
    initComponents();
    this.idUserLogin = idUserLogin;
    setupButtonGroup();
    setupDragAndDrop();
    System.out.println("DEBUG: BuatLaporan dibuka oleh ID User = " + idUserLogin);
}

    private void setupButtonGroup() {
        kriteriaGroup = new ButtonGroup();
        kriteriaGroup.add(jRKekerasan);
        kriteriaGroup.add(jRKorupsi);
        kriteriaGroup.add(jRDiskriminasi);
        kriteriaGroup.add(jRHAM);

        lblNamaFile = new JLabel("Belum ada file dipilih");
        lblNamaFile.setFont(new java.awt.Font("Segoe UI", 0, 10));
        getContentPane().add(lblNamaFile);
        lblNamaFile.setBounds(220, 355, 400, 25);
    }

    private void setupDragAndDrop() {
        new DropTarget(UnggahBukti, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles =
                            (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if (!droppedFiles.isEmpty()) {
                        selectedFile = droppedFiles.get(0);
                        lblNamaFile.setText("Dipilih: " + selectedFile.getName());
                        copyFileToUploads(selectedFile);
                        JOptionPane.showMessageDialog(null, "File berhasil diunggah: " + selectedFile.getName());
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Gagal drag file: " + e.getMessage());
                }
            }
        });
    }

    private void copyFileToUploads(File file) {
        try {
            File uploadDir = new File("uploads");
            if (!uploadDir.exists() && !uploadDir.mkdirs()) {
                throw new IOException("Gagal membuat folder uploads");
            }

            Files.copy(file.toPath(),
                    new File(uploadDir, file.getName()).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal menyalin file ke folder uploads.", e);
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat menyimpan file ke folder uploads.",
                    "Kesalahan Upload", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateNextIdLaporan() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String nextId = "LAP001";
        try {
            String lastId = (String) session
                    .createQuery("SELECT MAX(l.id_laporan) FROM Laporan l")
                    .uniqueResult();
            if (lastId != null && lastId.startsWith("LAP")) {
                int lastNum = Integer.parseInt(lastId.substring(3));
                nextId = String.format("LAP%03d", lastNum + 1);
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal membuat ID laporan.", e);
            JOptionPane.showMessageDialog(this,
                "Terjadi kesalahan saat membuat ID laporan baru.",
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
        } finally {
            session.close();
        }
        return nextId;
    }

    public void setModeEdit(Laporan laporan) {
        this.mode = "edit";
        this.idLaporan = laporan.getId_laporan();

        judulLaporan.setText(laporan.getJudul_laporan());
        jTextArea1.setText(laporan.getIsi_laporan());

        switch (laporan.getKategori()) {
            case "Kekerasan" -> jRKekerasan.setSelected(true);
            case "Korupsi" -> jRKorupsi.setSelected(true);
            case "Diskriminasi" -> jRDiskriminasi.setSelected(true);
            case "Pelanggaran HAM" -> jRHAM.setSelected(true);
        }

        if (laporan.getBukti() != null) {
            lblNamaFile.setText("File: " + laporan.getBukti());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        judulLaporan = new javax.swing.JTextField();
        jRKekerasan = new javax.swing.JRadioButton();
        jRKorupsi = new javax.swing.JRadioButton();
        jRDiskriminasi = new javax.swing.JRadioButton();
        jRHAM = new javax.swing.JRadioButton();
        Cancel = new javax.swing.JButton();
        Submit = new javax.swing.JButton();
        UnggahBukti = new javax.swing.JButton();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 560));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel4.setText("Kategori");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(50, 130, 70, 16);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setText("Deskripsi");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(50, 180, 70, 20);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setText("Unggah Bukti");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(50, 330, 70, 16);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 9)); // NOI18N
        jLabel7.setText("Data dan identitas Anda akan tetap bersifat rahasia.");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(50, 440, 240, 13);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel8.setText("Judul Laporan");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(50, 80, 70, 16);

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("© 2025 TruthLink — Developed by the TruthLink Team, Mulawarman University");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(170, 490, 450, 50);

        judulLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                judulLaporanActionPerformed(evt);
            }
        });
        getContentPane().add(judulLaporan);
        judulLaporan.setBounds(50, 96, 650, 30);

        jRKekerasan.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRKekerasan.setText("Kekerasan");
        jRKekerasan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRKekerasanActionPerformed(evt);
            }
        });
        getContentPane().add(jRKekerasan);
        jRKekerasan.setBounds(50, 150, 80, 20);

        jRKorupsi.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRKorupsi.setText("Korupsi");
        getContentPane().add(jRKorupsi);
        jRKorupsi.setBounds(130, 150, 72, 20);

        jRDiskriminasi.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRDiskriminasi.setText("Diskriminasi");
        getContentPane().add(jRDiskriminasi);
        jRDiskriminasi.setBounds(200, 150, 90, 20);

        jRHAM.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jRHAM.setText("Pelanggaran HAM");
        jRHAM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRHAMActionPerformed(evt);
            }
        });
        getContentPane().add(jRHAM);
        jRHAM.setBounds(290, 150, 110, 20);

        Cancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Cancel.setText("Batal");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        getContentPane().add(Cancel);
        Cancel.setBounds(280, 450, 100, 30);

        Submit.setBackground(new java.awt.Color(0, 0, 102));
        Submit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Submit.setForeground(new java.awt.Color(255, 255, 255));
        Submit.setText("Kirim");
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });
        getContentPane().add(Submit);
        Submit.setBounds(390, 450, 100, 30);

        UnggahBukti.setForeground(new java.awt.Color(102, 102, 102));
        UnggahBukti.setText("Masukan Disini");
        UnggahBukti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UnggahBuktiActionPerformed(evt);
            }
        });
        getContentPane().add(UnggahBukti);
        UnggahBukti.setBounds(50, 350, 650, 90);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
        getContentPane().add(jTextArea1);
        jTextArea1.setBounds(50, 200, 650, 120);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Logo.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 0, 40, 40);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jPanel1);
        jPanel1.setBounds(440, 130, 260, 60);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Beranda");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel16);
        jLabel16.setBounds(340, 10, 60, 20);

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Panduan");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel17);
        jLabel17.setBounds(410, 10, 80, 20);

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Riwayat");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel18);
        jLabel18.setBounds(563, 10, 70, 20);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Laporan");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel19);
        jLabel19.setBounds(490, 10, 70, 20);

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Keluar");
        jLabel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel20);
        jLabel20.setBounds(650, 12, 70, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Membuat Laporan `1.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 751, 534);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRHAMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRHAMActionPerformed
        
    }//GEN-LAST:event_jRHAMActionPerformed

    private void judulLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_judulLaporanActionPerformed
        
    }//GEN-LAST:event_judulLaporanActionPerformed

    private void jRKekerasanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRKekerasanActionPerformed
        
    }//GEN-LAST:event_jRKekerasanActionPerformed

    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        String judul = judulLaporan.getText().trim();
        String isi = jTextArea1.getText().trim();
        String kategori = "";

        if (jRKekerasan.isSelected()) kategori = "Kekerasan";
        else if (jRKorupsi.isSelected()) kategori = "Korupsi";
        else if (jRDiskriminasi.isSelected()) kategori = "Diskriminasi";
        else if (jRHAM.isSelected()) kategori = "Pelanggaran HAM";

        if (judul.isEmpty() || isi.isEmpty() || kategori.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap isi semua kolom yang diperlukan!");
            return;
        }

       if (idUserLogin == null || idUserLogin.isEmpty()) {
        JOptionPane.showMessageDialog(this, "ID Pelapor tidak ditemukan. Silakan login ulang.");
        return;
        }
        System.out.println("DEBUG: Submit laporan oleh ID User = " + idUserLogin);


                Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            User pelapor = session.get(User.class, idUserLogin);
            if (pelapor == null) {
                JOptionPane.showMessageDialog(this, "Data user pelapor tidak ditemukan di database!");
                return;
            }

            Laporan laporan;
            if (mode.equals("edit")) {
                laporan = session.get(Laporan.class, idLaporan);
                if (laporan == null) {
                    JOptionPane.showMessageDialog(this, "Data laporan tidak ditemukan!");
                    return;
                }
            } else {
                laporan = new Laporan();
                laporan.setId_laporan(generateNextIdLaporan());
            }

            laporan.setUserPelapor(pelapor);

            laporan.setJudul_laporan(judul);
            laporan.setIsi_laporan(isi);
            laporan.setTanggal_laporan(new java.util.Date());
            laporan.setKategori(kategori);
            laporan.setStatus("Belum Diproses");
            laporan.setBukti(selectedFile != null ? selectedFile.getName() : null);

            if (mode.equals("edit")) {
                session.update(laporan);
                JOptionPane.showMessageDialog(this, "Laporan berhasil diperbarui!");
            } else {
                session.save(laporan);
                JOptionPane.showMessageDialog(this, "Laporan baru berhasil dikirim!");
            }

            tx.commit();

            new HistoryUser(idUserLogin).setVisible(true);
            this.dispose();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }

    }//GEN-LAST:event_SubmitActionPerformed

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        new MainUser(idUserLogin).setVisible(true);
        this.dispose();

    }//GEN-LAST:event_CancelActionPerformed

    private void UnggahBuktiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UnggahBuktiActionPerformed
    JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File Bukti Laporan");

        javax.swing.filechooser.FileNameExtensionFilter filter =
            new javax.swing.filechooser.FileNameExtensionFilter(
                "File Bukti (.jpg, .png, .pdf)", "jpg", "png", "pdf");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            
            long maxSize = 100 * 1024 * 1024;
            if (selectedFile.length() > maxSize) {
                JOptionPane.showMessageDialog(this,
                    "Ukuran file terlalu besar! Maksimal 100 MB.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                selectedFile = null;
                return;
            }
            
            String fileName = selectedFile.getName().toLowerCase();
            if (!(fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".pdf"))) {
                JOptionPane.showMessageDialog(this,
                    "Format file tidak diperbolehkan! Gunakan .jpg, .png, atau .pdf.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                selectedFile = null;
                return;
            }

            lblNamaFile.setText("Dipilih: " + selectedFile.getName());

            try {
                copyFileToUploads(selectedFile);
                JOptionPane.showMessageDialog(this, "File berhasil diunggah!");
            } catch (Exception e) {
                logger.log(java.util.logging.Level.SEVERE, "Gagal mengunggah file.", e);
                JOptionPane.showMessageDialog(this,
                    "Gagal mengunggah file.\n" + e.getMessage(),
                    "Kesalahan Upload",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_UnggahBuktiActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        new MainUser(idUserLogin).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        new GuideUser(idUserLogin).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        new HistoryUser(idUserLogin).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        new BuatLaporan(idUserLogin).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new Home().setVisible(true);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel20MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new BuatLaporan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel;
    private javax.swing.JButton Submit;
    private javax.swing.JButton UnggahBukti;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRDiskriminasi;
    private javax.swing.JRadioButton jRHAM;
    private javax.swing.JRadioButton jRKekerasan;
    private javax.swing.JRadioButton jRKorupsi;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField judulLaporan;
    // End of variables declaration//GEN-END:variables

}
