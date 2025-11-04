package entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "pelapor")
@PrimaryKeyJoinColumn(name = "id_user")
public class Pelapor extends User {

    @Temporal(TemporalType.DATE)
    private Date tanggal_lahir;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_kelamin", nullable = false)
    private JenisKelamin jenis_kelamin;

    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal_daftar;

    public Pelapor() {}

    public Pelapor(String id_user, String nama, String username, String password, String email,
                   Date tanggal_lahir, JenisKelamin jenis_kelamin, Date tanggal_daftar) {
        super(id_user, nama, username, password, email);
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.tanggal_daftar = tanggal_daftar;
    }

    public Date getTanggal_lahir() { return tanggal_lahir; }
    public void setTanggal_lahir(Date tanggal_lahir) { this.tanggal_lahir = tanggal_lahir; }

    public JenisKelamin getJenis_kelamin() { return jenis_kelamin; }
    public void setJenis_kelamin(JenisKelamin jenis_kelamin) { this.jenis_kelamin = jenis_kelamin; }

    public Date getTanggal_daftar() { return tanggal_daftar; }
    public void setTanggal_daftar(Date tanggal_daftar) { this.tanggal_daftar = tanggal_daftar; }

    public enum JenisKelamin {Pria, Wanita}
}
