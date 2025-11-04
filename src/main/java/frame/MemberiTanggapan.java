package frame;
import entity.Laporan;
import com.mycompany.linkkk.HibernateUtil;
import entity.Tanggapan;
import entity.Lembaga;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.Transaction;
import entity.Admin;


public class MemberiTanggapan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MemberiTanggapan.class.getName());
    private Laporan laporan;
    private Admin adminLogin;

    public MemberiTanggapan() {
        initComponents();
        loadComboBoxLembaga();
        loadComboBoxStatus();
    }

    public MemberiTanggapan(Laporan laporan, Admin adminLogin) {
    initComponents();
    this.laporan = laporan;
    this.adminLogin = adminLogin;
    isiDataLaporan();
    loadComboBoxLembaga();
    loadComboBoxStatus();
}

    
    private void isiDataLaporan() {
        if (laporan == null) return;

        Tanggal.setText(laporan.getTanggal_laporan() != null ? laporan.getTanggal_laporan().toString() : "-");
        JudulLaporan.setText(laporan.getJudul_laporan());
        TingkatKeparahan.setText(String.valueOf(laporan.getTingkat_keparahan()));
        Kategori.setText(laporan.getKategori());
        comboStatus.setSelectedItem(laporan.getStatus() != null ? laporan.getStatus().replace("_", " ") : "-- Pilih Status --");

        Tanggal.setEditable(false);
        JudulLaporan.setEditable(false);
        TingkatKeparahan.setEditable(false);
        Kategori.setEditable(false);
    }
    
    private void loadComboBoxLembaga() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            List<Lembaga> lembagaList = session.createQuery("FROM Lembaga", Lembaga.class).list();

            jComboBox1.removeAllItems();
            jComboBox1.addItem("-- Pilih Lembaga --");

            for (Lembaga lembaga : lembagaList) {
                jComboBox1.addItem(lembaga.getNama_lembaga());
            }

            if (lembagaList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data lembaga di database!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data lembaga: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            }
        }
    
    private String generateIdTanggapan(Session session) {
        String lastId = (String) session.createQuery(
                "SELECT t.id_tanggapan FROM Tanggapan t ORDER BY t.id_tanggapan DESC")
                .setMaxResults(1)
                .uniqueResult();

        int nextNum = 1;
        if (lastId != null && lastId.startsWith("TGP")) {
            try {
                nextNum = Integer.parseInt(lastId.substring(3)) + 1;
            } catch (NumberFormatException e) {
                logger.warning("Format ID terakhir tidak valid: " + lastId);
            }
        }

        return "TGP" + String.format("%03d", nextNum);
    }


    private void simpanTanggapan(boolean teruskanKeLembaga) {
        if (laporan == null) {
            JOptionPane.showMessageDialog(this, "Tidak ada laporan yang dipilih!");
            return;
        }

        String isi = IsiTanggapan.getText().trim();
        if (isi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Isi tanggapan tidak boleh kosong!");
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // 🔹 ID Tanggapan Otomatis (TGP001, TGP002, dst)
            String idBaru = generateIdTanggapan(session);

            Tanggapan tanggapan = new Tanggapan();
            tanggapan.setId_tanggapan(idBaru);
            tanggapan.setTanggal_tanggapan(new Date());
            tanggapan.setIsi_tanggapan(isi);
            tanggapan.setLaporan(laporan);
            tanggapan.setAdmin(adminLogin);

            if (teruskanKeLembaga) {
                String namaLembagaDipilih = (String) jComboBox1.getSelectedItem();
                if (namaLembagaDipilih == null || namaLembagaDipilih.equals("-- Pilih Lembaga --")) {
                    JOptionPane.showMessageDialog(this, "Pilih lembaga tujuan terlebih dahulu!");
                    return;
                }

                Lembaga lembaga = session.createQuery(
                        "FROM Lembaga WHERE nama_lembaga = :nama", Lembaga.class)
                        .setParameter("nama", namaLembagaDipilih)
                        .uniqueResult();

                if (lembaga == null) {
                    JOptionPane.showMessageDialog(this, "Lembaga tidak ditemukan di database!");
                    return;
                }

                tanggapan.setLembaga(lembaga);
                tanggapan.setStatus(Tanggapan.Status.Dalam_Proses);
                laporan.setStatus("Diteruskan ke Lembaga");
            } else {
                tanggapan.setStatus(Tanggapan.Status.Selesai);
                laporan.setStatus("Laporan selesai ditangani");
            }

            // 🔹 Perbarui status laporan dari ComboBox
            String selectedStatus = (String) comboStatus.getSelectedItem();
            if (selectedStatus == null || selectedStatus.equals("-- Pilih Status --")) {
                JOptionPane.showMessageDialog(this, "Pilih status tanggapan terlebih dahulu!");
                return;
            }
            laporan.setStatus(selectedStatus);

            // 🔹 Simpan ke database
            session.persist(tanggapan);
            session.merge(laporan);
            tx.commit();

            JOptionPane.showMessageDialog(this,
                    teruskanKeLembaga
                            ? "Laporan berhasil diteruskan ke lembaga!\n(ID: " + idBaru + ")"
                            : "Tanggapan berhasil dikirim ke pelapor!\n(ID: " + idBaru + ")"
            );

            new AdminBagianMembacaLaporan(adminLogin).setVisible(true);
            this.dispose();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menyimpan tanggapan: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    
    private void loadComboBoxStatus() {
        comboStatus.removeAllItems();
        comboStatus.addItem("-- Pilih Status --");
        for (entity.Tanggapan.Status status : entity.Tanggapan.Status.values()) {
            comboStatus.addItem(status.toString().replace("_", " "));
        }
    }      
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnKirim = new javax.swing.JButton();
        btnTeruskan = new javax.swing.JButton();
        Kategori = new javax.swing.JTextField();
        TingkatKeparahan = new javax.swing.JTextField();
        JudulLaporan = new javax.swing.JTextField();
        Tanggal = new javax.swing.JTextField();
        IsiTanggapan = new javax.swing.JTextArea();
        jLabel20 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        comboStatus = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 560));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Buat Tanggapan");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(40, 60, 200, 40);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel13.setText("Kirim ke Lembaga");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(500, 160, 110, 20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel14.setText("Status");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(40, 270, 80, 30);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel15.setText("Kategori Laporan");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(40, 230, 100, 30);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel16.setText("Tingkat Keparahan");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(40, 190, 90, 30);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel17.setText("Judul Laporan");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(40, 150, 80, 30);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel18.setText("Tanggal");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(40, 110, 80, 30);

        btnKirim.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnKirim.setText("Kirim ke Pelapor");
        btnKirim.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKirimMouseClicked(evt);
            }
        });
        btnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKirimActionPerformed(evt);
            }
        });
        getContentPane().add(btnKirim);
        btnKirim.setBounds(220, 493, 160, 30);

        btnTeruskan.setBackground(new java.awt.Color(0, 0, 102));
        btnTeruskan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTeruskan.setForeground(new java.awt.Color(255, 255, 255));
        btnTeruskan.setText("Teruskan ke Lembaga");
        btnTeruskan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTeruskanMouseClicked(evt);
            }
        });
        btnTeruskan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTeruskanActionPerformed(evt);
            }
        });
        getContentPane().add(btnTeruskan);
        btnTeruskan.setBounds(390, 493, 160, 30);

        Kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KategoriActionPerformed(evt);
            }
        });
        getContentPane().add(Kategori);
        Kategori.setBounds(150, 230, 260, 26);

        TingkatKeparahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TingkatKeparahanActionPerformed(evt);
            }
        });
        getContentPane().add(TingkatKeparahan);
        TingkatKeparahan.setBounds(150, 190, 260, 26);

        JudulLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JudulLaporanActionPerformed(evt);
            }
        });
        getContentPane().add(JudulLaporan);
        JudulLaporan.setBounds(150, 150, 260, 26);

        Tanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TanggalActionPerformed(evt);
            }
        });
        getContentPane().add(Tanggal);
        Tanggal.setBounds(150, 110, 260, 26);

        IsiTanggapan.setColumns(20);
        IsiTanggapan.setRows(5);
        getContentPane().add(IsiTanggapan);
        IsiTanggapan.setBounds(50, 350, 650, 140);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel20.setText("Isi Tanggapan");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(40, 320, 80, 30);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(500, 180, 160, 40);

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboStatusActionPerformed(evt);
            }
        });
        getContentPane().add(comboStatus);
        comboStatus.setBounds(150, 270, 160, 26);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Kembali");
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(660, 10, 70, 22);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Memberi Tanggapan.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 760, 530);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void KategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_KategoriActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnKirimMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKirimMouseClicked
        simpanTanggapan(false);
    }//GEN-LAST:event_btnKirimMouseClicked

    private void btnTeruskanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTeruskanMouseClicked
        simpanTanggapan(true);
    }//GEN-LAST:event_btnTeruskanMouseClicked

    private void btnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKirimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnKirimActionPerformed

    private void TanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TanggalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalActionPerformed

    private void JudulLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JudulLaporanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JudulLaporanActionPerformed

    private void TingkatKeparahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TingkatKeparahanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TingkatKeparahanActionPerformed

    private void btnTeruskanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTeruskanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTeruskanActionPerformed

    private void comboStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboStatusActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        AdminBagianMembacaLaporan adminBagianMembacaLaporan = new AdminBagianMembacaLaporan(adminLogin);
        adminBagianMembacaLaporan.setVisible(true);
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2MouseClicked

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new MemberiTanggapan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea IsiTanggapan;
    private javax.swing.JTextField JudulLaporan;
    private javax.swing.JTextField Kategori;
    private javax.swing.JTextField Tanggal;
    private javax.swing.JTextField TingkatKeparahan;
    private javax.swing.JButton btnKirim;
    private javax.swing.JButton btnTeruskan;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables
}
