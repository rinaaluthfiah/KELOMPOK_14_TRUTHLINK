/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import entity.Tanggapan;
import entity.Laporan;
import entity.Admin;
import entity.Lembaga;
import com.mycompany.linkkk.HibernateUtil;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JOptionPane;
import org.hibernate.Session;


public class AdminBagianMembacaTanggapan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AdminBagianMembacaTanggapan.class.getName());

    private Admin adminLogin;

    public AdminBagianMembacaTanggapan(Admin adminLogin) {
        initComponents();
        this.adminLogin = adminLogin;
        loadDataTanggapan();
        isiComboStatusTanggapan();
        setupSearchPlaceholder();
        setupListeners();
    }

    public AdminBagianMembacaTanggapan() {
        initComponents();
        isiComboStatusTanggapan();
        loadDataTanggapan();
        setupListeners();
    }


    private void setupListeners() {
        // Filter status
        ddStatus.addActionListener(e -> applyFilter());

        // Live search
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });
    }

    private void loadDataTanggapan() {
    try {
        Dao.TanggapanIml dao = new Dao.TanggapanIml();
        List<Tanggapan> list = dao.getAll();

        DefaultTableModel model = (DefaultTableModel) tbTanggapan.getModel();
        model.setRowCount(0);

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada data tanggapan yang tersedia.",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Tanggapan t : list) {
            model.addRow(new Object[]{
                t.getId_tanggapan(),
                (t.getLaporan() != null) ? t.getLaporan().getId_laporan() : "-",
                (t.getAdmin() != null) ? t.getAdmin().getId_user() : "-",
                (t.getLembaga() != null) ? t.getLembaga().getId_lembaga() : "-",
                t.getIsi_tanggapan(),
                t.getTanggal_tanggapan(),
                (t.getStatus() != null) ? t.getStatus().name().replace("_", " ") : "-"
            });
        }

        tbTanggapan.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        resizeColumnWidth(tbTanggapan);
        tbTanggapan.setRowHeight(26);

    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal memuat data tanggapan", e);
        JOptionPane.showMessageDialog(this, "Gagal memuat data tanggapan: " + e.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }}

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


    private void isiComboStatusTanggapan() {
        try {
            ddStatus.removeAllItems();
            ddStatus.addItem("Pilih Status");
            
            for (entity.Tanggapan.Status status : entity.Tanggapan.Status.values()) {
                ddStatus.addItem(status.name().replace("_", " "));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Gagal memuat status tanggapan: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        private void searchLaporan(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tbTanggapan.getModel();
        model.setRowCount(0);
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            var query = session.createQuery(
                "FROM Laporan l " +
                "WHERE lower(l.id_laporan)        LIKE :kw " +
                "   OR lower(l.admin.id_user) LIKE :kw " +
                "   OR lower(l.judul_laporan)      LIKE :kw ",
                Laporan.class
            );

            String kw = (keyword == null ? "" : keyword).toLowerCase();
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
    
    private void applyFilter() {
        String keyword = jTextField2.getText().trim().toLowerCase();
        String selectedStatus = (String) ddStatus.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) tbTanggapan.getModel();
        model.setRowCount(0);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM Tanggapan t WHERE 1=1");

            if (selectedStatus != null && !selectedStatus.equalsIgnoreCase("Pilih Status")) {
                hql.append(" AND t.status = :statusEnum");
            }
            if (!keyword.isEmpty() && !keyword.equals("search")) {
                hql.append(" AND (lower(t.id_tanggapan) LIKE :kw "
                         + "OR lower(t.isi_tanggapan) LIKE :kw "
                         + "OR lower(t.laporan.id_laporan) LIKE :kw "
                         + "OR lower(t.admin.id_user) LIKE :kw)");
            }

            var query = session.createQuery(hql.toString(), Tanggapan.class);

            if (selectedStatus != null && !selectedStatus.equalsIgnoreCase("Pilih Status")) {
                entity.Tanggapan.Status statusEnum =
                        entity.Tanggapan.Status.valueOf(selectedStatus.replace(" ", "_"));
                query.setParameter("statusEnum", statusEnum);
            }

            if (!keyword.isEmpty() && !keyword.equals("search")) {
                query.setParameter("kw", "%" + keyword + "%");
            }

            List<Tanggapan> list = query.list();

            if (list == null || list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tidak ada data sesuai filter.", "Informasi",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Tanggapan t : list) {
                model.addRow(new Object[]{
                    t.getId_tanggapan(),
                    (t.getLaporan() != null) ? t.getLaporan().getId_laporan() : "-",
                    (t.getAdmin() != null) ? t.getAdmin().getId_user() : "-",
                    (t.getLembaga() != null) ? t.getLembaga().getId_lembaga() : "-",
                    t.getIsi_tanggapan(),
                    t.getTanggal_tanggapan(),
                    (t.getStatus() != null) ? t.getStatus().name().replace("_", " ") : "-"
                });
            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memfilter tanggapan", e);
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat memfilter tanggapan.\n" + e.getMessage(),
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void setupSearchPlaceholder() {
        jTextField2.setText("search");
        jTextField2.setForeground(java.awt.Color.GRAY);

        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextField2.getText().equals("search")) {
                    jTextField2.setText("");
                    jTextField2.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextField2.getText().trim().isEmpty()) {
                    jTextField2.setText("search");
                    jTextField2.setForeground(java.awt.Color.GRAY);
                    loadDataTanggapan();
                }
            }
        });

        jTextField2.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            void update() {
                if (!jTextField2.getText().equals("search")) {
                    jTextField2.setForeground(java.awt.Color.BLACK);
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

        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ddStatus = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbTanggapan = new javax.swing.JTable();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        jTextField1.setText("search");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(760, 550));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Tanggapan");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(150, 40, 130, 40);

        ddStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ddStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddStatusActionPerformed(evt);
            }
        });
        getContentPane().add(ddStatus);
        ddStatus.setBounds(630, 82, 120, 30);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Hapus");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(180, 90, 60, 16);

        jLabel4.setText("Beranda");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 50, 110, 16);

        jLabel5.setText("  Tanggapan");
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(40, 94, 100, 16);

        jLabel6.setText("  Lembaga");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(40, 116, 100, 20);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Keluar");
        jLabel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel8);
        jLabel8.setBounds(653, 10, 70, 20);

        jLabel9.setText("  Laporan");
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel9);
        jLabel9.setBounds(40, 70, 100, 16);

        jLabel2.setText("Status Laporan:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(540, 86, 90, 20);

        tbTanggapan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Tanggapan", "ID Laporan", "ID User", "ID Lembaga", "Isi Tanggapan", "Tanggal Tanggapan", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTanggapan.getTableHeader().setReorderingAllowed(false);
        tbTanggapan.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tbTanggapanAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tbTanggapan);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(150, 120, 590, 390);

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2);
        jTextField2.setBounds(280, 90, 250, 20);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Admin Bagian Membaca Tanggapan (2).png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 750, 520);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void ddStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddStatusActionPerformed
    applyFilter();
    }//GEN-LAST:event_ddStatusActionPerformed

    private void tbTanggapanAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tbTanggapanAncestorAdded

    }//GEN-LAST:event_tbTanggapanAncestorAdded

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
    BerandaAdmin berandaAdminPAdmin = new BerandaAdmin(adminLogin);
    berandaAdminPAdmin.setVisible(true);
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
    AdminBagianMembacaLaporan adminBagianMembacaLaporan = new AdminBagianMembacaLaporan(adminLogin);
    adminBagianMembacaLaporan.setVisible(true);
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
    AdminBagianMembacaTanggapan adminBagianMembacaTanggapan = new AdminBagianMembacaTanggapan(adminLogin);
    adminBagianMembacaTanggapan.setVisible(true);
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
    AdminBagianMembacaLembaga adminBagianMembacaLembaga = new AdminBagianMembacaLembaga(adminLogin);
    adminBagianMembacaLembaga.setVisible(true);
    this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
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
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
    int selectedRow = tbTanggapan.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih tanggapan yang ingin dihapus terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

    String idTanggapan = tbTanggapan.getValueAt(selectedRow, 0).toString();
    int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus tanggapan dengan ID: " + idTanggapan + "?",
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

    if (confirm != JOptionPane.YES_OPTION) return;

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        session.beginTransaction();
        Tanggapan tanggapan = session.get(Tanggapan.class, idTanggapan);

        if (tanggapan == null) {
            JOptionPane.showMessageDialog(this, "Tanggapan tidak ditemukan di database!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        session.remove(tanggapan);
        session.getTransaction().commit();

        JOptionPane.showMessageDialog(this, "Tanggapan berhasil dihapus!");
        logger.info("Admin " + (adminLogin != null ? adminLogin.getId_user() : "unknown")
                + " menghapus tanggapan ID: " + idTanggapan);

        applyFilter(); // refresh tabel

    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal menghapus tanggapan", e);
        JOptionPane.showMessageDialog(this,
                "Terjadi kesalahan saat menghapus tanggapan.\n" + e.getMessage(),
                "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jLabel3MouseClicked

    /**
     * @param args the command line arguments
     */


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminBagianMembacaTanggapan().setVisible(true));
    }   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ddStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable tbTanggapan;
    // End of variables declaration//GEN-END:variables
}
