/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class AdminBagianMembacaLembaga extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AdminBagianMembacaLembaga.class.getName());
    private entity.Admin adminLogin;
    
    public AdminBagianMembacaLembaga() {
    initComponents();
    loadDataLembaga();
    isiComboJenisLembaga();
    setupSearchPlaceholder();
    setupKontakValidation();
}


    private void loadDataLembaga() {
        try {
            Dao.LembagaIml dao = new Dao.LembagaIml();
            List<entity.Lembaga> list = dao.getAll();

            DefaultTableModel model = (DefaultTableModel) tbLembaga.getModel();
            model.setRowCount(0);

            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Belum ada data lembaga yang terdaftar.",
                        "Informasi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (entity.Lembaga lembaga : list) {
                model.addRow(new Object[]{
                    lembaga.getId_lembaga(),
                    lembaga.getNama_lembaga(),
                    lembaga.getAlamat(),
                    lembaga.getKontak(),
                    (lembaga.getJenis_lembaga() != null)
                            ? lembaga.getJenis_lembaga().name().replace("_", " ")
                            : "-"
                });
            }

            tbLembaga.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            resizeColumnWidth(tbLembaga);
            tbLembaga.setRowHeight(26);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat data lembaga", e);
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data lembaga: " + e.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
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

        private void isiComboJenisLembaga() {
            if (dropDownJL.getActionListeners().length > 0) {
                dropDownJL.removeActionListener(dropDownJL.getActionListeners()[0]);
            }

            dropDownJL.removeAllItems();
            dropDownJL.addItem("Pilih Jenis");
            for (entity.Lembaga.JenisLembaga jenis : entity.Lembaga.JenisLembaga.values()) {
                dropDownJL.addItem(jenis.toString().replace("_", " "));
            }
            dropDownJL.setSelectedIndex(0);

            dropDownJL.addActionListener(e -> dropDownJLActionPerformed(e));
        }

        private void searchLembaga(String keyword) {
            Dao.LembagaIml dao = new Dao.LembagaIml();
            List<entity.Lembaga> list;

            if (keyword == null || keyword.trim().isEmpty() || keyword.equalsIgnoreCase("search")) {
                list = dao.getAll();
            } else {
                try (var session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession()) {
                    String hql = "FROM Lembaga l WHERE " +
                                 "LOWER(l.id_lembaga) LIKE :key OR " +
                                 "LOWER(l.nama_lembaga) LIKE :key OR " +
                                 "LOWER(l.alamat) LIKE :key OR " +
                                 "LOWER(l.kontak) LIKE :key OR " +
                                 "LOWER(l.jenis_lembaga) LIKE :key";
                        
                    var query = session.createQuery(hql, entity.Lembaga.class);
                    query.setParameter("key", "%" + keyword.toLowerCase() + "%");
                    list = query.list();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }

            DefaultTableModel model = (DefaultTableModel) tbLembaga.getModel();
            model.setRowCount(0);

            for (entity.Lembaga lembaga : list) {
                model.addRow(new Object[]{
                    lembaga.getId_lembaga(),
                    lembaga.getNama_lembaga(),
                    lembaga.getAlamat(),
                    lembaga.getKontak(),
                    (lembaga.getJenis_lembaga() != null)
                        ? lembaga.getJenis_lembaga().name().replace("_", " ")
                        : "-"
                });
            }
        }
        
        private void setupKontakValidation() {
            kontakLembaga.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    String text = kontakLembaga.getText();

                   if (!text.matches("\\d*")) {
                        jLabel7.setText("Input hanya boleh berupa angka (0–9)");
                    } else {
                        jLabel7.setText("");
                    }
                }
            });
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
                        searchLembaga(""); 
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

        public AdminBagianMembacaLembaga(entity.Admin adminLogin) {
    initComponents();
    this.adminLogin = adminLogin;

    loadDataLembaga();
    isiComboJenisLembaga();
    setupSearchPlaceholder();

    jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { searchLembaga(jTextField1.getText().trim()); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { searchLembaga(jTextField1.getText().trim()); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { searchLembaga(jTextField1.getText().trim()); }
    });

    System.out.println("DEBUG: Admin login ID = " + adminLogin.getId_user());
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        alamatLembaga = new javax.swing.JTextField();
        kontakLembaga = new javax.swing.JTextField();
        namaLembaga = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        dropDownJL = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        Hapus = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Tambah = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLembaga = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 550));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel2.setText("Jenis Lembaga");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(450, 190, 120, 16);

        jLabel3.setText("Kontak");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(450, 120, 100, 16);

        jLabel4.setText("Alamat Lembaga");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(160, 190, 100, 16);

        jLabel5.setText("Nama Lembaga");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(160, 120, 90, 16);
        getContentPane().add(alamatLembaga);
        alamatLembaga.setBounds(160, 210, 260, 30);

        kontakLembaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kontakLembagaActionPerformed(evt);
            }
        });
        getContentPane().add(kontakLembaga);
        kontakLembaga.setBounds(450, 136, 270, 30);

        namaLembaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaLembagaActionPerformed(evt);
            }
        });
        getContentPane().add(namaLembaga);
        namaLembaga.setBounds(160, 136, 260, 30);

        jTextField1.setText("search");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(330, 90, 320, 20);

        dropDownJL.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        dropDownJL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dropDownJLActionPerformed(evt);
            }
        });
        getContentPane().add(dropDownJL);
        dropDownJL.setBounds(450, 210, 270, 30);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setText("Lembaga");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(150, 40, 130, 40);

        Hapus.setForeground(new java.awt.Color(255, 255, 255));
        Hapus.setText("Hapus");
        Hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HapusMouseClicked(evt);
            }
        });
        getContentPane().add(Hapus);
        Hapus.setBounds(260, 86, 50, 20);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Keluar");
        jLabel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(650, 10, 70, 20);

        jLabel10.setText("Lembaga");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel10);
        jLabel10.setBounds(50, 114, 90, 16);

        jLabel11.setText("Laporan");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel11);
        jLabel11.setBounds(50, 62, 90, 20);

        jLabel12.setText("Tanggapan");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel12);
        jLabel12.setBounds(50, 86, 90, 20);

        Tambah.setForeground(new java.awt.Color(255, 255, 255));
        Tambah.setText("Tambah");
        Tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahMouseClicked(evt);
            }
        });
        getContentPane().add(Tambah);
        Tambah.setBounds(180, 86, 50, 20);

        jLabel14.setText("  Beranda");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(30, 44, 110, 16);

        tbLembaga.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Lembaga", "Nama Lembaga", "Alamat", "Kontak", "Kategori Lembaga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbLembaga.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbLembaga);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 250, 590, 260);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Admin Bagian Membaca Lembaga (1).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 750, 520);

        jLabel7.setText("jLabel7");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(450, 140, 270, 20);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void namaLembagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaLembagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_namaLembagaActionPerformed

    private void dropDownJLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dropDownJLActionPerformed
        // TODO add your handling code here:
        Object selected = dropDownJL.getSelectedItem();
            if (selected == null) return; 
                System.out.println("Jenis lembaga yang dipilih: " + selected.toString());
    }//GEN-LAST:event_dropDownJLActionPerformed

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        BerandaAdmin berandaAdmin = new BerandaAdmin(adminLogin);
        berandaAdmin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        AdminBagianMembacaLaporan adminBagianMembacaLaporan = new AdminBagianMembacaLaporan(adminLogin);
        adminBagianMembacaLaporan.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        AdminBagianMembacaTanggapan adminBagianMembacaTanggapan = new AdminBagianMembacaTanggapan(adminLogin);
        adminBagianMembacaTanggapan.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        AdminBagianMembacaLembaga adminBagianMembacaLembaga = new AdminBagianMembacaLembaga(adminLogin);
        adminBagianMembacaLembaga.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
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
    }//GEN-LAST:event_jLabel9MouseClicked

    private void kontakLembagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kontakLembagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kontakLembagaActionPerformed

    private void HapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HapusMouseClicked
    int selectedRow = tbLembaga.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
                "Pilih lembaga yang ingin dihapus terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String idLembaga = tbLembaga.getValueAt(selectedRow, 0).toString();
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus lembaga dengan ID: " + idLembaga + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

    if (confirm != JOptionPane.YES_OPTION) return;

    try (var session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession()) {
        var tx = session.beginTransaction();
        try {
            entity.Lembaga lembaga = session.get(entity.Lembaga.class, idLembaga);
            if (lembaga == null) {
                JOptionPane.showMessageDialog(this,
                        "Lembaga tidak ditemukan di database!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            session.remove(lembaga);
            tx.commit();

            JOptionPane.showMessageDialog(this, "Lembaga berhasil dihapus!");
            logger.info("Admin menghapus lembaga ID: " + idLembaga);
            searchLembaga(jTextField1.getText().trim());

        } catch (Exception ex) {
            tx.rollback();
            logger.log(java.util.logging.Level.SEVERE, "Gagal menghapus lembaga", ex);
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus lembaga: " + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Kesalahan saat membuka session Hibernate", e);
        JOptionPane.showMessageDialog(this,
                "Kesalahan sistem: tidak dapat membuka koneksi ke database.",
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_HapusMouseClicked

    private void TambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseClicked
    String nama = namaLembaga.getText().trim();
    String alamat = alamatLembaga.getText().trim();
    String kontak = kontakLembaga.getText().trim();
    String jenisStr = (String) dropDownJL.getSelectedItem();

    if (nama.isEmpty() || alamat.isEmpty() || kontak.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Semua kolom (Nama, Alamat, dan Kontak) harus diisi.",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!kontak.matches("\\d+")) {
        JOptionPane.showMessageDialog(this,
                "Kontak hanya boleh berisi angka (0–9).",
                "Input tidak valid", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (jenisStr == null || jenisStr.equals("Pilih Jenis")) {
        JOptionPane.showMessageDialog(this,
                "Silakan pilih jenis lembaga terlebih dahulu.",
                "Input tidak lengkap", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (var session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession()) {
        var tx = session.beginTransaction();

        try {
            entity.Lembaga.JenisLembaga jenis =
                    entity.Lembaga.JenisLembaga.valueOf(jenisStr.replace(" ", "_"));

            String lastId = (String) session.createQuery(
                    "SELECT l.id_lembaga FROM Lembaga l ORDER BY l.id_lembaga DESC")
                    .setMaxResults(1)
                    .uniqueResult();

            int nextNum = 1;
            if (lastId != null && lastId.length() > 1) {
                nextNum = Integer.parseInt(lastId.substring(1)) + 1;
            }

            String idBaru = "L" + String.format("%03d", nextNum);

            entity.Lembaga lembaga = new entity.Lembaga();
            lembaga.setId_lembaga(idBaru);
            lembaga.setNama_lembaga(nama);
            lembaga.setAlamat(alamat);
            lembaga.setKontak(kontak);
            lembaga.setJenis_lembaga(jenis);

            session.persist(lembaga);
            tx.commit();

            JOptionPane.showMessageDialog(this, "Lembaga berhasil ditambahkan!");
            logger.info("Admin menambahkan lembaga baru: " + idBaru);

            loadDataLembaga();
            namaLembaga.setText("");
            alamatLembaga.setText("");
            kontakLembaga.setText("");
            dropDownJL.setSelectedIndex(0);

        } catch (Exception ex) {
            tx.rollback();
            logger.log(java.util.logging.Level.SEVERE, "Gagal menambah lembaga", ex);
            JOptionPane.showMessageDialog(this,
                    "Gagal menambah lembaga: " + ex.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Kesalahan saat membuka session Hibernate", e);
        JOptionPane.showMessageDialog(this,
                "Kesalahan sistem: tidak dapat membuka koneksi ke database.",
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_TambahMouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new AdminBagianMembacaLembaga().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Hapus;
    private javax.swing.JLabel Tambah;
    private javax.swing.JTextField alamatLembaga;
    private javax.swing.JComboBox<String> dropDownJL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField kontakLembaga;
    private javax.swing.JTextField namaLembaga;
    private javax.swing.JTable tbLembaga;
    // End of variables declaration//GEN-END:variables
}
