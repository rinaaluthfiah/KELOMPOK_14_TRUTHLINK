package entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tanggapan")
public class Tanggapan {

    @Id
    private String id_tanggapan;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal_tanggapan;

    private String isi_tanggapan;

    // Relasi ke Laporan (setiap Tanggapan untuk satu Laporan)
    @ManyToOne
    @JoinColumn(name = "id_laporan")
    private Laporan laporan;

    // Relasi ke Admin 
    @ManyToOne
    @JoinColumn(name = "id_user_admin")  // ✅ ubah ke nama kolom di tabel
    private Admin admin;

    // Relasi ke Lembaga (optional)
    @ManyToOne
    @JoinColumn(name = "id_lembaga")
    private Lembaga lembaga;
    
    // Enum untuk status
    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        Diterima,
        Dalam_Proses,
        Selesai,
        Ditolak
    }
    
    public Tanggapan() {}

    public Tanggapan(String id_tanggapan, Date tanggal_tanggapan, String isi_tanggapan,
                     Laporan laporan, Admin admin, Lembaga lembaga) {
        this.id_tanggapan = id_tanggapan;
        this.tanggal_tanggapan = tanggal_tanggapan;
        this.isi_tanggapan = isi_tanggapan;
        this.laporan = laporan;
        this.admin = admin;
        this.lembaga = lembaga;
    }

    // Getter dan Setter
    public String getId_tanggapan() {
        return id_tanggapan;
    }

    public void setId_tanggapan(String id_tanggapan) {
        this.id_tanggapan = id_tanggapan;
    }
    
    public Status getStatus() {
    return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getTanggal_tanggapan() {
        return tanggal_tanggapan;
    }

    public void setTanggal_tanggapan(Date tanggal_tanggapan) {
        this.tanggal_tanggapan = tanggal_tanggapan;
    }

    public String getIsi_tanggapan() {
        return isi_tanggapan;
    }

    public void setIsi_tanggapan(String isi_tanggapan) {
        this.isi_tanggapan = isi_tanggapan;
    }

    public Laporan getLaporan() {
        return laporan;
    }

    public void setLaporan(Laporan laporan) {
        this.laporan = laporan;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Lembaga getLembaga() {
        return lembaga;
    }

    public void setLembaga(Lembaga lembaga) {
        this.lembaga = lembaga;
    }
}
