package frame;

import com.mycompany.linkkk.HibernateUtil;
import entity.Laporan;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import java.awt.Desktop;             
import java.io.File;                 
import java.io.IOException;

public class AdminBagianMembacaLaporan extends javax.swing.JFrame {
    
private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AdminBagianMembacaLaporan.class.getName());
    private entity.Admin adminLogin;

    public AdminBagianMembacaLaporan(entity.Admin adminLogin) {
        initComponents();
        this.adminLogin = adminLogin;
        loadDataLaporan();
        setupSearchPlaceholder();
        loadComboBoxKategori();
        
        jComboBox1.addActionListener(evt -> applyFilter());
        
        jTextField1.addActionListener(evt -> applyFilter());
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { applyFilter(); }
        });

        System.out.println("DEBUG: Admin login ID = " + adminLogin.getId_user());
    }

    private void loadDataLaporan() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Laporan> list = session.createQuery("FROM Laporan", Laporan.class).list();

            for (Laporan lap : list) {
                String idPelapor = (lap.getUserPelapor() != null)
                        ? lap.getUserPelapor().getId_user() : "-";

                model.addRow(new Object[]{
                    lap.getId_laporan() != null ? lap.getId_laporan() : "-",
                    idPelapor,
                    lap.getJudul_laporan() != null ? lap.getJudul_laporan() : "-",
                    lap.getIsi_laporan() != null ? lap.getIsi_laporan() : "-",
                    lap.getKategori() != null ? lap.getKategori() : "-",
                    lap.getBukti() != null ? lap.getBukti() : "-",
                    lap.getTanggal_laporan() != null ? lap.getTanggal_laporan().toString() : "-",
                    lap.getTingkat_keparahan() != null ? lap.getTingkat_keparahan().toString() : "-"
                });
            }

            jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            resizeColumnWidth(jTable1);
            jTable1.setRowHeight(26);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data laporan: " + e.getMessage());
        }
    }
    
    private void resizeColumnWidth(javax.swing.JTable table) {
    final var columnModel = table.getColumnModel();
    for (int column = 0; column < table.getColumnCount(); column++) {
        int width = 100;
        for (int row = 0; row < table.getRowCount(); row++) {
            var renderer = table.getCellRenderer(row, column);
            var comp = table.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width + 10, width);
        }
        if (width > 400) width = 400;
        columnModel.getColumn(column).setPreferredWidth(width);
    }
}

    private void searchLaporan(String keyword) {
            if (keyword.trim().isEmpty()) {
                loadDataLaporan();
                return;
            }

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                var query = session.createQuery(
                    "FROM Laporan l " +
                    "WHERE lower(l.id_laporan) LIKE :kw " +
                    "   OR lower(l.userPelapor.id_user) LIKE :kw " +
                    "   OR lower(l.judul_laporan) LIKE :kw ", 
                    Laporan.class
                );

                String kw = (keyword == null ? "" : keyword.trim().toLowerCase());
                query.setParameter("kw", "%" + kw + "%");

                List<Laporan> list = query.list();

                for (Laporan lap : list) {
                    String idPelapor = "-";
                    if (lap.getUserPelapor() != null && lap.getUserPelapor().getId_user() != null) {
                        idPelapor = lap.getUserPelapor().getId_user();
                    }

                    model.addRow(new Object[]{
                        lap.getId_laporan() != null ? lap.getId_laporan() : "-",
                        idPelapor,
                        lap.getJudul_laporan() != null ? lap.getJudul_laporan() : "-",
                        lap.getIsi_laporan() != null ? lap.getIsi_laporan() : "-",
                        lap.getKategori() != null ? lap.getKategori() : "-",
                        lap.getBukti() != null ? lap.getBukti() : "-",
                        lap.getTanggal_laporan() != null ? lap.getTanggal_laporan().toString() : "-",
                        lap.getTingkat_keparahan() != null ? lap.getTingkat_keparahan().toString() : "-"
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Gagal mencari data laporan: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    private void loadComboBoxKategori() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<String> kategoriList = session.createQuery(
                "SELECT DISTINCT l.kategori FROM Laporan l WHERE l.kategori IS NOT NULL",
                String.class
            ).list();

            jComboBox1.removeAllItems();
            jComboBox1.addItem("Pilih Kategori");

            for (String kategori : kategoriList) {
                jComboBox1.addItem(kategori);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat kategori: " + e.getMessage());
        }
    }
        
        private void applyFilter() {
        String keyword = jTextField1.getText().trim().toLowerCase();
        String selectedKategori = (String) jComboBox1.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Laporan l WHERE 1=1");

            // filter kategori
            if (selectedKategori != null && !selectedKategori.equalsIgnoreCase("Pilih Kategori")) {
                hql.append(" AND l.kategori = :kategori");
            }

            // filter keyword search
            if (!keyword.isEmpty() && !keyword.equalsIgnoreCase("search")) {
                hql.append(" AND (lower(l.id_laporan) LIKE :kw "
                         + "OR lower(l.judul_laporan) LIKE :kw "
                         + "OR lower(l.userPelapor.id_user) LIKE :kw "
                         + "OR lower(l.isi_laporan) LIKE :kw)");
            }

            var query = session.createQuery(hql.toString(), Laporan.class);

            if (selectedKategori != null && !selectedKategori.equalsIgnoreCase("Pilih Kategori")) {
                query.setParameter("kategori", selectedKategori);
            }
            if (!keyword.isEmpty() && !keyword.equalsIgnoreCase("search")) {
                query.setParameter("kw", "%" + keyword + "%");
            }

            List<Laporan> list = query.list();
            for (Laporan lap : list) {
                model.addRow(new Object[]{
                    lap.getId_laporan(),
                    lap.getUserPelapor() != null ? lap.getUserPelapor().getId_user() : "-",
                    lap.getJudul_laporan(),
                    lap.getIsi_laporan(),
                    lap.getKategori(),
                    lap.getBukti(),
                    lap.getTanggal_laporan(),
                    lap.getTingkat_keparahan()
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memfilter laporan: " + e.getMessage());
        }
    }
        private void setupSearchPlaceholder() {
        jTextField1.setText("search");
        jTextField1.setForeground(java.awt.Color.GRAY);

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().equals("search")) {
                    jTextField1.setText("");
                    jTextField1.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextField1.getText().trim().isEmpty()) {
                    jTextField1.setText("search");
                    jTextField1.setForeground(java.awt.Color.GRAY);
                    loadDataLaporan(); // panggil ulang data laporan
                }
            }
        });

        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            void update() {
                if (!jTextField1.getText().equals("search")) {
                    jTextField1.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(760, 550));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Laporan");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(150, 30, 130, 50);

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(250, 50, 340, 20);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox1);
        jComboBox1.setBounds(600, 50, 140, 20);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Beri Tanggapan");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel2);
        jLabel2.setBounds(630, 86, 100, 20);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hapus");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(230, 86, 40, 20);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Edit");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4);
        jLabel4.setBounds(180, 86, 30, 20);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Keluar");
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(660, 10, 70, 20);

        jLabel7.setText("  Laporan");
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(40, 60, 100, 16);

        jLabel8.setText("  Lembaga");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel8);
        jLabel8.setBounds(40, 110, 100, 16);

        jLabel9.setText("  Tanggapan");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(40, 84, 100, 16);

        jLabel10.setText("Beranda");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel10);
        jLabel10.setBounds(30, 36, 110, 20);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Laporan", "ID User", "Judul Laporan", "Isi Laporan", "Kategori Laporan", "Bukti", "Tanggal Laporan", "Tingkat Keparahan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 110, 600, 390);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Admin Bagian Membaca Laporan (1).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, -10, 750, 520);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        applyFilter();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());

        if (evt.getClickCount() == 2 && col == 5) {
            try {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Pilih laporan terlebih dahulu!");
                    return;
                }

                String namaFile = jTable1.getValueAt(selectedRow, 5).toString().trim();
                if (namaFile == null || namaFile.equals("-") || namaFile.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Laporan ini tidak memiliki file bukti.");
                    return;
                }

                File uploadsFolder = new File(System.getProperty("user.dir"), "uploads");
                File file = new File(uploadsFolder, namaFile);
                System.out.println("DEBUG: Mencoba membuka -> " + file.getAbsolutePath());

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "File tidak ditemukan di folder uploads!\nNama file: " + namaFile);
                    return;
                }
                if (!file.getCanonicalPath().startsWith(uploadsFolder.getCanonicalPath())) {
                    JOptionPane.showMessageDialog(this, "Akses file di luar folder uploads tidak diizinkan!");
                    return;
                }

                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Gagal membuka file: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih laporan yang ingin diberi tanggapan!");
            return;
        }

        String idLaporan = jTable1.getValueAt(selectedRow, 0).toString();

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Laporan laporan = session.get(Laporan.class, idLaporan);
            if (laporan != null) {
                MemberiTanggapan tanggapanFrame = new MemberiTanggapan(laporan, adminLogin);
                tanggapanFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Laporan tidak ditemukan!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka tanggapan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
            int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih laporan yang ingin dihapus!");
            return;
        }

        String idLaporan = jTable1.getValueAt(selectedRow, 0).toString();

        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus laporan dengan ID: " + idLaporan + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.beginTransaction();
                Laporan laporan = session.get(Laporan.class, idLaporan);
                if (laporan != null) {
                    session.remove(laporan);
                    session.getTransaction().commit();
                    JOptionPane.showMessageDialog(this, "Laporan berhasil dihapus!");
                    loadDataLaporan();
                } else {
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan di database!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus laporan: " + e.getMessage());
            } finally {
                session.close();
            }
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih laporan untuk diedit!");
            return;
        }

        String idLaporan = jTable1.getValueAt(selectedRow, 0).toString();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Laporan laporan = session.get(Laporan.class, idLaporan);
        session.close();

        if (laporan != null) {
            AdminEditLaporan editFrame = new AdminEditLaporan(laporan, adminLogin);
            editFrame.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Laporan tidak ditemukan!");
        }
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            new Home().setVisible(true);
        }
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
    new BerandaAdmin(adminLogin).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
    new AdminBagianMembacaLaporan(adminLogin).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
    new AdminBagianMembacaTanggapan(adminLogin).setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
    new AdminBagianMembacaLembaga(adminLogin).setVisible(true); 
    this.dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

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

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
