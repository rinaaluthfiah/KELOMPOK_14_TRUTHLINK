# KELOMPOK 14 TRUTHLINK

Anggota Kelompok:
1. Chiqo Nanda Rial Pratama | 2409116046

2. Isrina Luthfiah | 2409116003

3. Rini Wulandari | 2409116048

4. Nadila Putri | 2409116052

# TruthLink
<p align="center">
  <img width="400" height="500" alt="Tak berjudul27_20251023092038" src="https://github.com/user-attachments/assets/3951c018-eaa5-4577-a087-b31533d5c1a8" />
      
Deskripsi Singkat Program:

Program ini merupakan sistem pelaporan digital berbasis Website yang  dirancang sebagai upaya mendukung tercapainya Sustainable Development Goals (SDG) 16: Peace, Justice, and Strong Institutions, dengan menyediakan wadah pelaporan yang aman, cepat, dan transparan bagi masyarakat. Melalui sistem ini, pengguna dapat membuat laporan mengenai tindak kekerasan, korupsi, diskriminasi, pelanggaran HAM, hingga penyalahgunaan kekuasaan, yang kemudian diverifikasi dan ditindaklanjuti oleh pihak admin maupun lembaga terkait.

Program ini bertujuan untuk membangun manajemen laporan yang efisien dan terstruktur, memastikan setiap laporan ditangani dengan proses verifikasi yang transparan dan akurat, serta meningkatkan akuntabilitas lembaga dalam menindaklanjuti laporan masyarakat. Secara keseluruhan, sistem ini diharapkan dapat meningkatkan transparansi, melindungi pelapor, serta mendorong terciptanya pemerintahan yang lebih adil dan berintegritas melalui pemanfaatan teknologi digital.

# Flowchart dan Use Case Diagram

## Flowchart

<details>
  <summary>1. Flowchart Menu Utama</summary>
   <img width="400" height="500" alt="Tak berjudul27_20251023092038" src="https://github.com/user-attachments/assets/3951c018-eaa5-4577-a087-b31533d5c1a8" />
</details>

<details>
  <summary>2. Flowcharrt Registrasi</summary>
   
</details>

<details>
  <summary>3. Flowchart Login User</summary>
   
</details>

<details>
  <summary>4. Flowchart About</summary>
   
</details>

<details>
  <summary>5. Flowchart Guide</summary>
   
</details>

<details>
  <summary>6. Flowchart Others</summary>
   
</details>

<details>
  <summary>7. Flowchart Menu Admin</summary>
   
</details>
   
<details>
  <summary>8. Flowchart Laporan</summary>
   
</details>
   
<details>
  <summary>9. Flowchart Tanggapan</summary>
   
</details>

<details>
  <summary>10. Flowchart Lembaga</summary>
   
</details>    

<details>
  <summary>11. Flowchart Lembaga</summary>
   
</details>    

<details>
  <summary>12. Flowchart Menu Pelapor</summary>
   
</details>  

<details>
  <summary>13. Flowchart Lihat Pentunjuk</summary>
   
</details>  

<details>
  <summary>14. Flowchart Buat Laporan</summary>
   
</details>  

<details>
  <summary>15. Flowchart Baca Laporan</summary>
   
</details>  

## Use Case Diagram 
<details>
  <summary>Use Case Diagram TruthLink</summary>
   
</details>

# Fitur Program
Program ini dilengkapi dengan berbagai fitur utama untuk mendukung pengelolaan data laporan secara efektif, efisien, dan transparan, baik bagi pelapor maupun admin, yaitu:

## 1. Create 

## 2. Read

## 3.  Update 📝 contohnya
Fitur Update memungkinkan user untuk melakukan perubahan data laporan yang sudah ada.
Contohnya:
- Pelapor dapat memperbarui isi laporan jika terdapat kesalahan atau ingin menambahkan bukti baru. Tapi jika laporan sudah dikirimkan ke lembaga maka laporan tidak bisa di edit lagi.
- Admin dapat mengubah tingkat keparahan dari laporan yang dikirimkan pelapor dan juga status laporan, seperti Diterima / Dalam Proses /Ditolak / Selesai
 
<p align="center">
<img width="911" height="626" alt="Screenshot 2025-11-02 182210" src="https://github.com/user-attachments/assets/04730e0c-6c6e-4d23-bb00-d0a8bfebece5"  width="400">
</p>

Dengan fitur ini, seluruh pihak dapat memperbarui informasi laporan secara dinamis sesuai perkembangan proses verifikasi.

## 4. Delete  🗑


## 5. Searching 🔎 & Filter
Fitur ini berfungsi untuk memudahkan admin dalam menemukan laporan/tanggapan tertentu secara cepat dan akurat.
Sistem menyediakan dua mekanisme pencarian data yang dapat digunakan secara terpisah maupun bersamaan:
1. Searching (Pencarian):
Admin dapat mengetik kata kunci tertentu (misalnya id_laporan, id_userPelapor, atau judul laporan) untuk menampilkan data yang relevan tanpa perlu menelusuri seluruh daftar laporan.

2. Filter (Penyaringan):
Admin juga dapat menyaring laporan berdasarkan kriteria tertentu, seperti:
- Kategori laporan: Kekerasan, Korupsi, Diskriminasi, dan Pelanggaran HAM
- Status laporan: Menunggu, Dalam Proses, Diterima, Ditolak, Selesai

3. Kombinasi Searching dan Filter:
Sistem juga mendukung penggunaan kedua fitur ini secara bersamaan.
Contohnya, admin mencari kata kunci “kampus” dan menyaring laporan dengan kategori laporan “kekerasan”, sehingga hasil yang ditampilkan benar-benar sesuai dengan kebutuhan pencarian.

Dengan adanya fitur ini, proses pengelolaan dan penelusuran laporan menjadi lebih efisien, cepat, dan terarah, baik untuk admin dalam melakukan verifikasi atau tindak lanjut laporan.

# Penerapan OOP
1. Encapsulation
   Encapsulation merupakan salah satu pilar utama dalam Pemrograman Berorientasi Objek (PBO) yang bertujuan untuk melindungi data dari akses langsung dari luar kelas.
Penerapan konsep ini dilakukan dengan menggunakan access modifier private pada atribut kelas dan menyediakan metode getter serta setter untuk mengakses atau memodifikasi nilai atribut tersebut. Pada sistem ini, encapsulation diterapkan pada kelas User yang memiliki atribut seperti id_user, nama, username, password, dan email. Semua atribut tersebut bersifat private dan hanya dapat diakses melalui metode `getId_user()`, `setNama()`, `getUsername()`, `setPassword()`, dan sebagainya.
   Selain pada kelas User, prinsip encapsulation juga diterapkan pada kelas lain seperti Admin, Pelapor, Laporan, dan Tanggapan, di mana setiap atribut private dikelola melalui metode getter dan setter. Hal ini menjaga integritas data serta keamanan informasi pengguna dalam sistem, karena setiap perubahan nilai atribut dilakukan secara terkontrol.
   
2. Inheritance
   Inheritance merupakan pilar OOP yang memungkinkan suatu kelas (child/subclass) mewarisi properti dan metode dari kelas lain (parent/superclass), sehingga mengurangi redundansi dan meningkatkan efisiensi kode.
   - Parent/Super Class
     Pada sistem ini, kelas User berperan sebagai superclass yang berisi atribut umum seperti `id_user`, `nama`, `username`, `password`, dan `email`. Kelas ini juga merupakan entitas JPA yang dipetakan ke tabel user di database dan menggunakan strategi pewarisan `@Inheritance(strategy = InheritanceType.JOINED)`. Melalui strategi ini, setiap subclass memiliki tabel tersendiri yang bergabung dengan tabel user berdasarkan kolom `id_user`.

      ```
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

        // Relasi ke tabel laporan (satu user bisa punya banyak laporan)
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
      }
      ```

   - Child/Sub Class
     Kelas turunan (subclass) mewarisi seluruh atribut dan perilaku dari User, serta menambahkan atribut baru yang lebih spesifik sesuai kebutuhan.
     
     a. Pelapor
        Kelas Pelapor mewarisi User dan menambahkan atribut tambahan seperti `tanggal_lahir`, `jenis_kelamin`, dan `tanggal_daftar`. Kelas ini merepresentasikan pengguna yang berperan sebagai pelapor kasus dalam sistem.
     
        ```
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

            // ==================== Getter & Setter ====================
        }
        ```

     b. Admin
        Kelas Admin juga mewarisi User dan menambahkan atribut `kontak_admin` untuk menyimpan informasi kontak administrator. Kelas ini digunakan untuk mengelola data pelapor dan memverifikasi laporan yang masuk ke sistem.

        ```
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
        ```

4. Abstraction
   
5. Polymorphism
   
6. Interface

# Struktur Folder/Package
<p align="center">
<img width="650" height="500" alt="image" src="https://github.com/user-attachments/assets/46da45ed-7f99-4020-bff6-8f12c46a11f2" />
<p>
1. Source Packages
  
2. Test Packages
   
3. Other Sources
   
6. Dependencies



# Library / Framework


# Cara Menggunakan Program



