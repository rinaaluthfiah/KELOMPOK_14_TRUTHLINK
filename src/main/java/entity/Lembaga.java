package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lembaga")
public class Lembaga {

    @Id
    @Column(name = "id_lembaga", length = 30, nullable = false)
    private String id_lembaga;

    @Column(name = "nama_lembaga", length = 100, nullable = false)
    private String nama_lembaga;

    @Column(name = "alamat", length = 255, nullable = false)
    private String alamat;

    @Column(name = "kontak", length = 50, nullable = false)
    private String kontak;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_lembaga", nullable = false)
    private JenisLembaga jenis_lembaga;
    
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = true)
    private User user;
    
    public enum JenisLembaga {
        Penegak_Hukum,
        Anti_Korupsi_Pengawasan,
        Perlindungan_Pencegahan_Kekerasan,
        Etika_Transparansi;    
    
        @Override
        public String toString() {
            return name().replace("_", " ");
        }
    }

    // ===================== Constructor =====================
    public Lembaga() {}

    public Lembaga(String id_lembaga, String nama_lembaga, String alamat, String kontak,
                   JenisLembaga jenis_lembaga, User user) {
        this.id_lembaga = id_lembaga;
        this.nama_lembaga = nama_lembaga;
        this.alamat = alamat;
        this.kontak = kontak;
        this.jenis_lembaga = jenis_lembaga;
        this.user = user;
    }

    // ===================== Getter & Setter =====================
    public String getId_lembaga() {
        return id_lembaga;
    }

    public void setId_lembaga(String id_lembaga) {
        this.id_lembaga = id_lembaga;
    }

    public String getNama_lembaga() {
        return nama_lembaga;
    }

    public void setNama_lembaga(String nama_lembaga) {
        this.nama_lembaga = nama_lembaga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public JenisLembaga getJenis_lembaga() {
        return jenis_lembaga;
    }

    public void setJenis_lembaga(JenisLembaga jenis_lembaga) {
        this.jenis_lembaga = jenis_lembaga;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
