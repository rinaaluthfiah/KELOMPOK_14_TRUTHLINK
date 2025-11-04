/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import com.mycompany.linkkk.HibernateUtil;
import entity.Laporan;
import entity.Tanggapan;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 *
 * @author ASUS
 */
public class HistoryUser extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(HistoryUser.class.getName());
    private String idUserPelapor;

    public HistoryUser() {
        initComponents();
    }

    public HistoryUser(String idUserPelapor) {
        initComponents();
        this.idUserPelapor = idUserPelapor;
        tableRiwayat();
    }

    private void tableRiwayat() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Laporan");
        model.addColumn("Judul");
        model.addColumn("Kategori");
        model.addColumn("Tanggal");
        model.addColumn("Status");
        model.addColumn("Isi Tanggapan");
        model.addColumn("Bukti");

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "FROM Laporan WHERE userPelapor.id_user = :idUserPelapor";
            var query = session.createQuery(hql, Laporan.class);
            query.setParameter("idUserPelapor", idUserPelapor);

            List<Laporan> laporanList = query.list();
            for (Laporan l : laporanList) {
                String isiTanggapan;
                try {
                    Tanggapan tanggapan = session.get(Tanggapan.class, l.getId_laporan());
                    if (tanggapan != null && tanggapan.getIsi_tanggapan() != null) {
                        isiTanggapan = tanggapan.getIsi_tanggapan();
                    } else {
                        isiTanggapan = "Belum ada tanggapan";
                    }
                } catch (Exception e) {
                    isiTanggapan = "Belum ada tanggapan";
                }

                model.addRow(new Object[]{
                        l.getId_laporan(),
                        l.getJudul_laporan(),
                        l.getKategori(),
                        l.getTanggal_laporan(),
                        (l.getStatus() != null) ? l.getStatus() : "Belum Diproses",
                        isiTanggapan,
                        l.getBukti() != null ? l.getBukti() : "-",
                });
            }

            tableRiwayat.setModel(model);
            autoResizeTableColumns(tableRiwayat);
            tableRiwayat.setRowHeight(26);

            tableRiwayat.setDefaultEditor(Object.class, null);
            tableRiwayat.getTableHeader().setReorderingAllowed(false);
            tableRiwayat.getTableHeader().setResizingAllowed(false);
            tableRiwayat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            tx.commit();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private void autoResizeTableColumns(javax.swing.JTable table) {
        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        final var columnModel = table.getColumnModel();

        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 100; // lebar minimum
            for (int row = 0; row < table.getRowCount(); row++) {
                var renderer = table.getCellRenderer(row, column);
                var comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 15, width);
            }
            // batas maksimum agar tabel tidak terlalu panjang
            if (width > 500) width = 500;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    private void lihatBuktiAction() {
    int selectedRow = tableRiwayat.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih laporan terlebih dahulu!");
        return;
    }

    String namaFile = tableRiwayat.getValueAt(selectedRow, 6).toString(); // kolom ke-6 = Bukti
    if (namaFile == null || namaFile.equals("-") || namaFile.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Laporan ini tidak memiliki file bukti.");
        return;
    }

    // Folder "uploads" di dalam direktori project
    File uploadsFolder = new File(System.getProperty("user.dir"), "uploads");
    File file = new File(uploadsFolder, namaFile);

    System.out.println("DEBUG: Mencoba membuka -> " + file.getAbsolutePath());

    if (!file.exists()) {
        JOptionPane.showMessageDialog(this, "File tidak ditemukan di folder uploads!\nNama file: " + namaFile);
        return;
    }

    try {
        Desktop.getDesktop().open(file);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Gagal membuka file: " + ex.getMessage());
        ex.printStackTrace();
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRiwayat = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(760, 560));
        setResizable(false);
        getContentPane().setLayout(null);

        tableRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Judul Laporan", "Kategori", "Tanggal", "Status", "Tanggapan", "Bukti"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
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
        tableRiwayat.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableRiwayat);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(20, 140, 710, 360);

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        jLabel2.setText("Riwayat Laporan");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(210, 60, 320, 50);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Beranda");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel16);
        jLabel16.setBounds(320, 10, 60, 20);

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Panduan");
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel17);
        jLabel17.setBounds(400, 10, 70, 20);

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Riwayat");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel18);
        jLabel18.setBounds(560, 10, 70, 20);

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Laporan");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel19);
        jLabel19.setBounds(480, 10, 70, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Lihat Bukti");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel3);
        jLabel3.setBounds(80, 110, 70, 20);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Hapus");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4);
        jLabel4.setBounds(660, 114, 50, 16);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Keluar");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel5);
        jLabel5.setBounds(650, 10, 70, 20);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Edit");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel6);
        jLabel6.setBounds(580, 114, 30, 16);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/History User.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 750, 530);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        new MainUser(idUserPelapor).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        new GuideUser(idUserPelapor).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel17MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        new HistoryUser(idUserPelapor).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void jLabel19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseClicked
        new BuatLaporan(idUserPelapor).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel19MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        lihatBuktiAction();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        int selectedRow = tableRiwayat.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih laporan yang ingin diedit!");
            return;
        }

        String idLaporan = tableRiwayat.getValueAt(selectedRow, 0).toString();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Laporan laporan = session.get(Laporan.class, idLaporan);

            if (laporan == null) {
                JOptionPane.showMessageDialog(this, "Data laporan tidak ditemukan!");
                return;
            }

            String status = laporan.getStatus() != null ? laporan.getStatus().trim().toLowerCase() : "belum diproses";

            if (status.equals("belum diproses")) {
                BuatLaporan editPage = new BuatLaporan(idUserPelapor);
                editPage.setModeEdit(laporan);
                editPage.setVisible(true);
                this.dispose();
            } else {
                String pesan;
                switch (status) {
                    case "diterima":
                    pesan = "Laporan tidak dapat diedit karena sudah diterima admin.";
                    break;
                    case "dalam proses":
                    pesan = "Laporan sedang dalam proses dan tidak dapat diedit.";
                    break;
                    case "selesai":
                    pesan = "Laporan telah selesai dan tidak dapat diedit.";
                    break;
                    case "ditolak":
                    pesan = "Laporan ditolak dan tidak dapat diedit.";
                    break;
                    default:
                    pesan = "Laporan tidak dapat diedit karena sudah diproses.";
                    break;
                }
                JOptionPane.showMessageDialog(this, pesan, "Akses Dibatasi", JOptionPane.WARNING_MESSAGE);
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
    int selectedRow = tableRiwayat.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih laporan yang ingin dihapus!");
        return;
    }

    String idLaporan = tableRiwayat.getValueAt(selectedRow, 0).toString();

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus laporan dengan ID: " + idLaporan + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Ambil laporan berdasarkan ID
            Laporan laporan = session.get(Laporan.class, idLaporan);
            if (laporan == null) {
                JOptionPane.showMessageDialog(this, "Laporan tidak ditemukan!");
                return;
            }

            // Hapus juga tanggapan yang terkait (jika ada)
            Tanggapan tanggapan = session.get(Tanggapan.class, idLaporan);
            if (tanggapan != null) {
                session.remove(tanggapan);
            }

            // Hapus laporan
            session.remove(laporan);
            tx.commit();

            JOptionPane.showMessageDialog(this, "Laporan berhasil dihapus!");
            tableRiwayat(); // refresh tabel setelah penghapusan

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }        // TODO add your handling code here:
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new HistoryUser().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tableRiwayat;
    // End of variables declaration//GEN-END:variables
}
