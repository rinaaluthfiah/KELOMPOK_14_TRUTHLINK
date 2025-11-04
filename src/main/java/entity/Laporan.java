package entity;

import entity.Lembaga.JenisLembaga;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "laporan")
public class Laporan {

    @Id
    @Column(name = "id_laporan", length = 10)
    private String id_laporan;

    @Column(name = "judul_laporan", length = 100)
    private String judul_laporan;

    @Column(name = "isi_laporan", columnDefinition = "TEXT")
    private String isi_laporan;
    
    @Column(name = "kategori_laporan", length = 50)
    private String kategori;
    
    @Enumerated(EnumType.STRING)
    private JenisLembaga jenis_lembaga;
    
    @Column(name = "bukti", length = 255)
    private String bukti;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tanggal_laporan")
    private Date tanggal_laporan;

    @Column(name = "status", length = 50)
    private String status;
    
    @Column(name = "Tingkat_Keparahan")
    private Double Tingkat_Keparahan;

    // 🔗 Relasi ke User
    @ManyToOne
    @JoinColumn(name = "id_user_pelapor", referencedColumnName = "id_user", nullable = false)
    private User userPelapor;

    // 🔗 Tambahan field opsional
    @Column(name = "id_tanggapan", nullable = true)
    private Integer id_tanggapan;
    
    @OneToMany(mappedBy = "laporan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tanggapan> tanggapan = new ArrayList<>();

    // ===== Getter Setter =====
    public String getId_laporan() { return id_laporan; }
    public void setId_laporan(String id_laporan) { this.id_laporan = id_laporan; }

    public String getJudul_laporan() { return judul_laporan; }
    public void setJudul_laporan(String judul_laporan) { this.judul_laporan = judul_laporan; }

    public String getIsi_laporan() { return isi_laporan; }
    public void setIsi_laporan(String isi_laporan) { this.isi_laporan = isi_laporan; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getBukti() { return bukti; }
    public void setBukti(String bukti) { this.bukti = bukti; }

    public Date getTanggal_laporan() { return tanggal_laporan; }
    public void setTanggal_laporan(Date tanggal_laporan) { this.tanggal_laporan = tanggal_laporan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Getter & Setter baru untuk relasi User
    public User getUserPelapor() { return userPelapor; }
    public void setUserPelapor(User userPelapor) { this.userPelapor = userPelapor; }

    // Getter & Setter untuk id_tanggapan
    public Integer getId_tanggapan() { return id_tanggapan; }
    public void setId_tanggapan(Integer id_tanggapan) { this.id_tanggapan = id_tanggapan; }

    public Double getTingkat_keparahan() {
        return Tingkat_Keparahan;
    }
    public void setTingkat_keparahan(Double tingkat_keparahan) {
        this.Tingkat_Keparahan = tingkat_keparahan;
    }
    
public List<Tanggapan> getTanggapan() {
    return tanggapan;
}
public void setTanggapan(List<Tanggapan> tanggapan) {
    this.tanggapan = tanggapan;
}

    
    public Object getPelapor() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    }
    
