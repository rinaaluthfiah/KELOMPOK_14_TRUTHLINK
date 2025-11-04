/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import com.mycompany.linkkk.HibernateUtil;
import entity.Laporan;
import entity.Tanggapan;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


public class MainUser extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger =
        java.util.logging.Logger.getLogger(MainUser.class.getName());

    private String idUserLogin;
    private entity.Pelapor pelaporLogin;

    public MainUser() {
        initComponents();
    }

    public MainUser(String idUserLogin) {
    initComponents();
    this.idUserLogin = idUserLogin;

    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        this.pelaporLogin = session.get(entity.Pelapor.class, idUserLogin);
        if (pelaporLogin != null) {
            System.out.println("DEBUG: Pelapor login = " + pelaporLogin.getNama());
        } else {
            System.out.println("elapor tidak ditemukan di database untuk ID: " + idUserLogin);
        }
    } catch (Exception e) {
        System.err.println("Gagal mengambil data pelapor: " + e.getMessage());
    }

    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentShown(ComponentEvent evt) {
            scaleImage();
            refreshData();
            tampilkanNamaPelapor();
        }

        @Override
        public void componentResized(ComponentEvent evt) {
            scaleImage();
        }
    });
}


    private void scaleImage() {
        try {
            java.net.URL imageURL = getClass().getResource("/picture/Main User.png");
            if (imageURL == null) {
                JOptionPane.showMessageDialog(this,
                        "Gambar tampilan tidak ditemukan.\nPastikan file ada di folder resources/picture.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                logger.warning("Gambar tidak ditemukan di /picture/Main User.png");
                return;
            }

            ImageIcon icon = new ImageIcon(imageURL);
            Image img = icon.getImage();
            Image scaled = img.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), Image.SCALE_SMOOTH);
            jLabel1.setIcon(new ImageIcon(scaled));

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal menampilkan gambar.", e);
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat memuat gambar.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshData() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No", "Judul Laporan", "Kategori", "Tanggal", "Status", "Tanggapan"}, 0
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Session session = null;
        Transaction tx = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Laporan> q = session.createQuery(
                    "FROM Laporan l LEFT JOIN FETCH l.tanggapan WHERE l.userPelapor.id_user = :idUser ORDER BY l.tanggal_laporan DESC",
                    Laporan.class
            );
            q.setParameter("idUser", idUserLogin);
            List<Laporan> laporanList = q.list();

            int no = 1;
            for (Laporan l : laporanList) {
                String tgl = (l.getTanggal_laporan() != null) ? fmt.format(l.getTanggal_laporan()) : "-";
                String isiTanggapan = "-";
                List<Tanggapan> tanggapanList = l.getTanggapan();
                if (tanggapanList != null && !tanggapanList.isEmpty()) {
                    Tanggapan tTerbaru = tanggapanList.get(tanggapanList.size() - 1);
                    if (tTerbaru.getIsi_tanggapan() != null && !tTerbaru.getIsi_tanggapan().trim().isEmpty()) {
                        isiTanggapan = tTerbaru.getIsi_tanggapan();
                    }
                }

                model.addRow(new Object[]{
                        no++, l.getJudul_laporan(), l.getKategori(), tgl, l.getStatus(), isiTanggapan
                });
            }

            TablePelapor.setModel(model);
            TablePelapor.getTableHeader().setReorderingAllowed(false);

            long total = laporanList.size();
            long diterima = laporanList.stream().filter(l -> "Diterima".equalsIgnoreCase(l.getStatus())).count();
            long diproses = laporanList.stream().filter(l -> "Sedang Diproses".equalsIgnoreCase(l.getStatus())).count();
            long ditolak = laporanList.stream().filter(l -> "Ditolak".equalsIgnoreCase(l.getStatus())).count();

            jLabel13.setText(String.valueOf(total));
            jLabel12.setText(String.valueOf(diterima));
            jLabel14.setText(String.valueOf(diproses));
            jLabel15.setText(String.valueOf(ditolak));

            tx.commit();

        } catch (org.hibernate.exception.JDBCConnectionException e) {
            if (tx != null) tx.rollback();
            logger.log(java.util.logging.Level.SEVERE, "Koneksi database gagal.", e);
            JOptionPane.showMessageDialog(this, "Tidak dapat terhubung ke database.\nPeriksa koneksi server.",
                    "Kesalahan Koneksi", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat data laporan.", e);
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat memuat data laporan.",
                    "Kesalahan Data", JOptionPane.ERROR_MESSAGE);

        } finally {
            if (session != null) session.close();
        }
    }
    
    public MainUser(entity.Pelapor pelaporLogin) {
        initComponents();
        this.pelaporLogin = pelaporLogin;
        System.out.println("DEBUG: Admin login ID = " + pelaporLogin.getId_user());
    }

    private void tampilkanNamaPelapor() {
        try {
            if (pelaporLogin != null && pelaporLogin.getNama() != null) {
                String namaPelapor = pelaporLogin.getNama();
                jLabel3.setText("Selamat Datang Kembali, " + namaPelapor + "!");
            } else {
                jLabel3.setText("Selamat Datang Kembali, Pelapor!");
            }
        } catch (Exception e) {
            jLabel3.setText("Selamat Datang Kembali, Pelapor!");
            System.err.println("Gagal menampilkan nama Pelapor: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton8 = new javax.swing.JButton();
        KirimLaporan = new javax.swing.JButton();
        LihatRiwayat = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePelapor = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        jButton8.setText("jButton8");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(750, 550));
        setResizable(false);
        getContentPane().setLayout(null);

        KirimLaporan.setBackground(new java.awt.Color(12, 32, 65));
        KirimLaporan.setForeground(new java.awt.Color(255, 255, 255));
        KirimLaporan.setText("Kirim Laporan");
        KirimLaporan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        KirimLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                KirimLaporanActionPerformed(evt);
            }
        });
        getContentPane().add(KirimLaporan);
        KirimLaporan.setBounds(20, 180, 160, 30);

        LihatRiwayat.setBackground(new java.awt.Color(12, 32, 65));
        LihatRiwayat.setForeground(new java.awt.Color(255, 255, 255));
        LihatRiwayat.setText("Lihat Riwayat Laporanku");
        LihatRiwayat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        LihatRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LihatRiwayatActionPerformed(evt);
            }
        });
        getContentPane().add(LihatRiwayat);
        LihatRiwayat.setBounds(200, 180, 210, 30);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Selamat Datang Kembali, Pelapor!");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(20, 70, 660, 32);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kelola laporan Anda dan berkontribusilah pada keadilan serta transparansi.");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(20, 114, 410, 10);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText(" TruthLink memungkinkan Anda untuk melaporkan kasus korupsi, kekerasan, atau pelanggaran hak asasi manusia secara aman dan rahasia. ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 120, 610, 30);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Laporan Anda membantu mewujudkan masyarakat yang adil dan transparan.");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(20, 140, 440, 20);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Ringkasan Laporan Anda:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(30, 250, 220, 20);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Ditolak");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(550, 286, 60, 20);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel9.setText("Sedang Diproses");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(420, 286, 90, 20);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Diterima");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel10);
        jLabel10.setBounds(270, 290, 70, 15);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Total Laporan");
        jLabel11.setToolTipText("");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(140, 286, 80, 20);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Laporan Terbaru Anda:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 370, 160, 16);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("0");
        jLabel12.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel12PropertyChange(evt);
            }
        });
        getContentPane().add(jLabel12);
        jLabel12.setBounds(310, 310, 10, 20);

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("0");
        jLabel13.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel13PropertyChange(evt);
            }
        });
        getContentPane().add(jLabel13);
        jLabel13.setBounds(170, 310, 10, 20);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("0");
        jLabel14.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel14PropertyChange(evt);
            }
        });
        getContentPane().add(jLabel14);
        jLabel14.setBounds(450, 310, 10, 20);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("0");
        jLabel15.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel15PropertyChange(evt);
            }
        });
        getContentPane().add(jLabel15);
        jLabel15.setBounds(590, 310, 10, 20);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
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
        jLabel17.setBounds(410, 10, 60, 20);

        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Riwayat");
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel18);
        jLabel18.setBounds(553, 10, 60, 20);

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Laporan");
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel19MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel19);
        jLabel19.setBounds(480, 10, 60, 20);

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
        jLabel20.setBounds(633, 10, 70, 20);

        TablePelapor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "Judul Laporan", "Tanggal", "Status", "Tanggapan", "Tindakan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablePelapor.getTableHeader().setReorderingAllowed(false);
        TablePelapor.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TablePelaporAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(TablePelapor);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(30, 400, 690, 100);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Main User.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 750, 530);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void KirimLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_KirimLaporanActionPerformed
    new BuatLaporan(idUserLogin).setVisible(true);
    this.dispose();

    }//GEN-LAST:event_KirimLaporanActionPerformed

    private void LihatRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LihatRiwayatActionPerformed
        new HistoryUser(idUserLogin).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LihatRiwayatActionPerformed

    private void TablePelaporAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TablePelaporAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TablePelaporAncestorAdded

    private void jLabel13PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel13PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13PropertyChange

    private void jLabel12PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel12PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel12PropertyChange

    private void jLabel14PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel14PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14PropertyChange

    private void jLabel15PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel15PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15PropertyChange

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
        try {
            new Home().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal keluar ke halaman Home.", e);
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan saat keluar aplikasi.",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_jLabel20MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MainUser().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton KirimLaporan;
    private javax.swing.JButton LihatRiwayat;
    private javax.swing.JTable TablePelapor;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
