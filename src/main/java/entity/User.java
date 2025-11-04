package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(generator = "user_id_gen")
    @GenericGenerator(name = "user_id_gen", strategy = "com.mycompany.linkkk.PelaporIdGenerator")
    @Column(name = "id_user", length = 10)
    private String id_user;

    private String nama;
    private String username;
    private String password;
    private String email;

    // 🔗 Relasi ke tabel laporan (satu user bisa punya banyak laporan)
    @OneToMany(mappedBy = "userPelapor", cascade = CascadeType.ALL)
    private List<Laporan> laporanList;

    // ==================== Constructor ====================
    public User() {}

    public User(String id_user, String nama, String username, String password, String email) {
        this.id_user = id_user;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // ==================== Getter & Setter ====================
    public String getId_user() { return id_user; }
    public void setId_user(String id_user) { this.id_user = id_user; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Laporan> getLaporanList() { return laporanList; }
    public void setLaporanList(List<Laporan> laporanList) { this.laporanList = laporanList; }
}
