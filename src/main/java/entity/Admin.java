package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id_user")
public class Admin extends User {

    @Column(name = "kontak_admin")
    private String kontak_admin;

    public Admin() {}

    public Admin(String id_user, String nama, String username, String password, String email, String kontak_admin) {
        super(id_user, nama, username, password, email);
        this.kontak_admin = kontak_admin;
    }

    public String getKontak_admin() {
        return kontak_admin;
    }

    public void setKontak_admin(String kontak_admin) {
        this.kontak_admin = kontak_admin;
    }
    
}
