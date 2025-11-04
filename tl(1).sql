-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 04, 2025 at 01:57 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tl`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_user` varchar(30) NOT NULL,
  `kontak_admin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_user`, `kontak_admin`) VALUES
('USR001', '081234500001'),
('USR002', '081234500002'),
('USR003', '081234500003'),
('USR004', '081234500004'),
('USR005', '081234500005'),
('USR006', '081234500006'),
('USR007', '081234500007'),
('USR008', '081234500008'),
('USR009', '081234500009'),
('USR010', '081234500010');

-- --------------------------------------------------------

--
-- Table structure for table `laporan`
--

CREATE TABLE `laporan` (
  `id_laporan` varchar(30) NOT NULL,
  `judul_laporan` varchar(50) NOT NULL,
  `isi_laporan` text NOT NULL,
  `kategori_laporan` varchar(50) DEFAULT NULL,
  `bukti` varchar(255) NOT NULL,
  `tanggal_laporan` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `tingkat_keparahan` double DEFAULT NULL,
  `id_user_pelapor` varchar(30) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `id_tanggapan` int(11) DEFAULT NULL,
  `jenis_lembaga` enum('Penegak_Hukum','Anti_Korupsi_Pengawasan','Perlindungan_Pencegahan_Kekerasan','Etika_Transparansi') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `laporan`
--

INSERT INTO `laporan` (`id_laporan`, `judul_laporan`, `isi_laporan`, `kategori_laporan`, `bukti`, `tanggal_laporan`, `tingkat_keparahan`, `id_user_pelapor`, `status`, `id_tanggapan`, `jenis_lembaga`) VALUES
('LAP001', 'Tindakan Kekerasan di Tempat Kerja', 'Terjadi tindakan kekerasan fisik antar rekan kerja di kantor pusat. Pelapor meminta perlindungan tenaga kerja.', 'Kekerasan', 'bukti_kekerasan_kerja1.png', '2024-07-02 00:30:00', NULL, 'PLP00001', 'Selesai', NULL, NULL),
('LAP002', 'Indikasi Korupsi Dana Proyek Desa', 'Dugaan penyalahgunaan dana proyek pembangunan jembatan oleh perangkat desa. Pelapor meminta audit keuangan segera.', 'Korupsi', 'bukti_korupsi_desa.pdf', '2024-07-03 01:45:00', NULL, 'PLP00004', NULL, NULL, NULL),
('LAP003', 'Pelanggaran HAM dalam Penahanan Warga', 'Seorang warga ditahan tanpa surat resmi. Pelapor menuntut pemeriksaan dan perlindungan hukum.', 'Pelanggaran HAM', 'bukti_ham_penahanan.jpeg', '2025-11-02 03:44:48', NULL, 'PLP00007', NULL, NULL, NULL),
('LAP004', 'Diskriminasi Gender di', 'Guru menolak siswa perempuan mengikuti kegiatan praktikum lapangan tanpa alasan jelas.', 'Diskriminasi', '', '2025-11-04 06:43:36', NULL, 'PLP00012', 'Belum Diproses', NULL, NULL),
('LAP006', 'Kekerasan Rumah Tangga di Lingkungan Warga', 'Pelapor menyaksikan tindak kekerasan dalam rumah tangga yang sering terjadi di lingkungan sekitar.', 'Kekerasan', 'bukti_kdrt1.jpeg', '2024-07-07 00:10:00', NULL, 'PLP00008', 'Diterima', NULL, NULL),
('LAP008', 'Kekerasan Verbal oleh Aparat', 'Aparat lalu lintas melakukan intimidasi verbal saat razia kendaraan.', 'Pelanggaran HAM', 'bukti_aparat_verbal.png', '2025-11-02 03:44:48', 4.5, 'PLP00020', 'Dalam Proses', NULL, NULL),
('LAP009', 'Pelanggaran HAM di Rumah Sakit', 'Pasien miskin tidak dilayani karena tidak memiliki asuransi. Pelapor menilai hal ini melanggar hak kesehatan.', 'Pelanggaran HAM', 'bukti_rumahsakit_ham.jpeg', '2025-11-02 03:51:12', NULL, 'PLP00018', NULL, NULL, NULL),
('LAP010', 'Korupsi Proyek Infrastruktur Kota', 'Tender proyek jalan raya di kota diduga dimanipulasi oleh oknum panitia lelang.', 'Korupsi', 'bukti_proyek_kota.pdf', '2024-07-13 07:20:00', 1, 'PLP00025', NULL, NULL, NULL),
('LAP011', 'Diskriminasi terhadap Difabel', 'Pelapor mengalami penolakan kerja karena disabilitas, padahal memenuhi syarat kompetensi.', 'Diskriminasi', 'bukti_difabel_penolakan.png', '2024-07-15 02:00:00', NULL, 'PLP00022', 'Dalam Proses', NULL, NULL),
('LAP012', 'Kekerasan terhadap Anak di Sekolah', 'Guru dilaporkan melakukan kekerasan fisik terhadap murid kelas 2.', 'Kekerasan', 'bukti_anak_sekolah.jpeg', '2024-07-16 00:40:00', NULL, 'PLP00030', NULL, NULL, NULL),
('LAP014', 'Korupsi di Lembaga Pendidikan', 'Dugaan manipulasi laporan keuangan oleh bendahara sekolah negeri di kota.', 'Korupsi', 'bukti_lembaga_pendidikan.pdf', '2024-07-19 04:00:00', NULL, 'PLP00028', NULL, NULL, NULL),
('LAP015', 'Diskriminasi Rasial di Tempat Umum', 'Pelapor mendapat perlakuan tidak adil di restoran karena perbedaan daerah asal.', 'Diskriminasi', 'bukti_diskriminasi_rasial.jpeg', '2024-08-01 01:15:00', NULL, 'PLP00009', NULL, NULL, NULL),
('LAP016', 'Pelanggaran HAM oleh Petugas Keamanan', 'Petugas keamanan menahan warga tanpa dasar hukum jelas.', 'Pelanggaran HAM', 'bukti_petugas_keamanan.png', '2025-11-02 03:44:48', NULL, 'PLP00033', NULL, NULL, NULL),
('LAP017', 'Kekerasan di Lembaga Sosial', 'Pelapor melaporkan tindak kekerasan staf panti sosial terhadap penghuni lansia.', 'Kekerasan', 'bukti_panti_sosial.pdf', '2024-08-03 02:30:00', NULL, 'PLP00011', NULL, NULL, NULL),
('LAP018', 'Korupsi dalam Pengadaan Barang Publik', 'Nilai pengadaan dinaikkan secara fiktif untuk keuntungan pribadi oleh panitia proyek.', 'Korupsi', 'bukti_pengadaan_barang.jpeg', '2024-08-05 03:00:00', NULL, 'PLP00040', NULL, NULL, NULL),
('LAP019', 'Diskriminasi terhadap Karyawan Kontrak', 'Karyawan kontrak tidak mendapatkan tunjangan dan cuti yang sama dengan pegawai tetap.', 'Diskriminasi', 'bukti_karyawan_kontrak.png', '2024-08-07 06:00:00', NULL, 'PLP00045', NULL, NULL, NULL),
('LAP020', 'Pelanggaran HAM dalam Penggusuran Warga', 'Warga digusur tanpa surat resmi dan tanpa kompensasi layak. Pelapor meminta intervensi hukum.', 'Pelanggaran HAM', 'bukti_penggusuran.pdf', '2025-11-02 03:44:48', NULL, 'PLP00050', 'Dalam Proses', NULL, NULL),
('LAP021', 'Korupsi Dana Sosial Daerah', 'Pelapor menemukan indikasi penyelewengan dana sosial yang seharusnya untuk warga miskin.', 'Korupsi', 'bukti_danasosial.pdf', '2024-07-20 01:10:00', NULL, 'PLP00002', NULL, NULL, NULL),
('LAP022', 'Kekerasan terhadap Pekerja Magang', 'Supervisor melakukan kekerasan verbal dan fisik terhadap mahasiswa magang.', 'Kekerasan', 'bukti_magang.png', '2024-07-21 02:00:00', NULL, 'PLP00003', NULL, NULL, NULL),
('LAP023', 'Pelanggaran HAM oleh Perusahaan Tambang', 'Perusahaan menutup akses air bersih bagi warga sekitar tambang tanpa kompensasi.', 'Pelanggaran HAM', 'bukti_tambang_ham.jpeg', '2025-11-02 03:51:55', NULL, 'PLP00006', NULL, NULL, NULL),
('LAP024', 'Diskriminasi Usia dalam Rekrutmen', 'Pelapor ditolak kerja karena usia melebihi 35 tahun, tanpa penilaian kemampuan.', 'Diskriminasi', 'bukti_diskriminasi_usia.pdf', '2024-07-23 05:20:00', NULL, 'PLP00009', 'Selesai', NULL, NULL),
('LAP025', 'Kekerasan Fisik di Rumah Tahanan', 'Pelapor melaporkan tindak kekerasan sipir terhadap tahanan di ruang isolasi.', 'Kekerasan', 'bukti_tahanan.jpeg', '2024-07-23 23:30:00', NULL, 'PLP00013', NULL, NULL, NULL),
('LAP026', 'Korupsi Dana Bantuan Pendidikan', 'Dana bantuan siswa miskin tidak disalurkan seluruhnya. Pelapor mencurigai manipulasi data penerima.', 'Korupsi', 'bukti_bantuan_siswa.pdf', '2024-07-25 01:00:00', NULL, 'PLP00016', NULL, NULL, NULL),
('LAP027', 'Penyalahgunaan Kekuasaan dalam Pemecatan', 'Seorang kepala instansi memecat pegawai tanpa dasar hukum dan prosedur yang jelas.', 'Pelanggaran HAM', 'bukti_pemecatan.jpeg', '2025-11-02 03:44:48', NULL, 'PLP00021', NULL, NULL, NULL),
('LAP028', 'Diskriminasi Agama di Tempat Kerja', 'Pelapor melaporkan tindakan diskriminatif terhadap pekerja yang menjalankan ibadah wajib.', 'Diskriminasi', 'bukti_agama.png', '2024-07-27 00:55:00', NULL, 'PLP00024', NULL, NULL, NULL),
('LAP029', 'Korupsi Dana Renovasi Kantor', 'Dana renovasi gedung kantor digunakan untuk pembelian pribadi oleh panitia proyek.', 'Korupsi', 'bukti_renovasi.pdf', '2024-07-28 06:15:00', NULL, 'PLP00026', NULL, NULL, NULL),
('LAP030', 'Kekerasan terhadap Jurnalis', 'Pelapor melaporkan adanya pemukulan terhadap wartawan saat peliputan aksi demo.', 'Kekerasan', 'bukti_jurnalis.jpeg', '2024-07-29 07:40:00', NULL, 'PLP00031', NULL, NULL, NULL),
('LAP031', 'Pelanggaran HAM dalam Penanganan Demonstrasi', 'Pelapor merekam tindakan kekerasan aparat terhadap massa demonstran damai.', 'Pelanggaran HAM', 'bukti_demo.pdf', '2025-11-02 03:51:12', NULL, 'PLP00034', NULL, NULL, NULL),
('LAP032', 'Diskriminasi Pendidikan terhadap Anak Daerah', 'Sekolah menolak murid karena berasal dari daerah terpencil tanpa fasilitas lengkap.', 'Diskriminasi', 'bukti_anak_daerah.png', '2024-08-01 02:10:00', NULL, 'PLP00036', NULL, NULL, NULL),
('LAP033', 'Korupsi Proyek Irigasi Desa', 'Proyek irigasi tidak selesai meskipun dana sudah 100% dicairkan. Pelapor menduga mark-up anggaran.', 'Korupsi', 'bukti_irigasi.pdf', '2024-08-02 05:30:00', NULL, 'PLP00039', NULL, NULL, NULL),
('LAP035', 'Kekerasan di Lingkungan Panti Asuhan', 'Pelapor melaporkan kekerasan fisik yang dilakukan pengurus terhadap anak panti.', 'Kekerasan', 'bukti_pantiasuhan.pdf', '2024-08-04 01:30:00', NULL, 'PLP00043', NULL, NULL, NULL),
('LAP036', 'Pelanggaran HAM pada Pengungsi', 'Pelapor melaporkan pengungsi tidak mendapat layanan kesehatan yang layak.', 'Pelanggaran HAM', 'bukti_pengungsi.png', '2025-11-02 03:44:48', NULL, 'PLP00044', NULL, NULL, NULL),
('LAP037', 'Korupsi Proyek IT Pemerintah', 'Anggaran pengadaan sistem informasi dinaikkan tanpa bukti pembelian yang sah.', 'Korupsi', 'bukti_itproyek.pdf', '2024-08-06 02:00:00', NULL, 'PLP00046', NULL, NULL, NULL),
('LAP038', 'Kekerasan di Lingkungan Kampus', 'Pelapor melaporkan senior yang melakukan perpeloncoan fisik terhadap mahasiswa baru.', 'Kekerasan', 'bukti_kampus.jpeg', '2024-08-07 06:00:00', NULL, 'PLP00047', NULL, NULL, NULL),
('LAP039', 'Diskriminasi di Fasilitas Publik', 'Pelapor mengalami perlakuan berbeda di rumah sakit karena status sosialnya.', 'Diskriminasi', 'bukti_rs_sosial.png', '2024-08-08 00:15:00', NULL, 'PLP00048', NULL, NULL, NULL),
('LAP041', 'AC Teknik lama dibaiki gaji gak turun turun', 'dekan nya anu', 'Korupsi', 'PerpustakaanJDBC.zip', '2025-11-02 08:20:21', NULL, 'PLP00053', 'Selesai', NULL, NULL),
('LAP042', 'Aku mau Berubah', 'takkanku berubah walau tag bersama kita takakan', 'Pelanggaran HAM', '', '2025-11-02 23:43:50', NULL, 'PLP00060', 'Belum Diproses', NULL, NULL),
('LAP043', 'Tenxi Memukul seorang pria', 'Wah parah bangett dehhh', 'Diskriminasi', 'Topologi Tree Kelompok 9.pdf', '2025-11-04 08:44:44', NULL, 'PLP00062', 'Belum Diproses', NULL, NULL),
('LAP044', 'BISMILLAH', 'OOOOO', 'Pelanggaran HAM', 'Virtual Private Network (VPN) Kelompok 5.pdf', '2025-11-04 12:56:49', NULL, 'PLP00012', 'Belum Diproses', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `lembaga`
--

CREATE TABLE `lembaga` (
  `id_lembaga` varchar(30) NOT NULL,
  `nama_lembaga` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `kontak` varchar(50) NOT NULL,
  `jenis_lembaga` enum('Penegak_Hukum','Anti_Korupsi_Pengawasan','Perlindungan_Pencegahan_Kekerasan','Etika_Transparansi') NOT NULL,
  `id_user` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `lembaga`
--

INSERT INTO `lembaga` (`id_lembaga`, `nama_lembaga`, `alamat`, `kontak`, `jenis_lembaga`, `id_user`) VALUES
('L001', 'Kejaksaan Negeri', 'Jl. Ampera Raya No. 10, Jakarta Selatan', '0217654321', 'Penegak_Hukum', NULL),
('L002', 'Komisi Pemberantasan Korupsi (KPK)', 'Jl. HR Rasuna Said Kav. C1, Jakarta Selatan', '02125578300', 'Anti_Korupsi_Pengawasan', NULL),
('L003', 'Komnas Perempuan', 'Jl. Latuharhary No. 4B, Jakarta Pusat', '0213903963', 'Perlindungan_Pencegahan_Kekerasan', NULL),
('L004', 'Komisi Etik dan Transparansi Publik', 'Jl. Sudirman No. 88, Jakarta Pusat', '0215678901', 'Etika_Transparansi', NULL),
('L006', 'Pembinaan', 'Jl, Lembaga Nihilism', '081302120452', 'Etika_Transparansi', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `pelapor`
--

CREATE TABLE `pelapor` (
  `id_user` varchar(30) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `jenis_kelamin` enum('Pria','Wanita') NOT NULL,
  `tanggal_daftar` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pelapor`
--

INSERT INTO `pelapor` (`id_user`, `tanggal_lahir`, `jenis_kelamin`, `tanggal_daftar`) VALUES
('PLP00001', '1999-02-10', 'Pria', '2024-05-01 01:00:00'),
('PLP000011', '2000-12-12', 'Pria', '2025-10-30 14:50:25'),
('PLP00002', '2001-07-12', 'Wanita', '2024-05-02 02:00:00'),
('PLP00003', '1998-03-20', 'Pria', '2024-05-03 03:00:00'),
('PLP00004', '2000-10-05', 'Wanita', '2024-05-04 00:30:00'),
('PLP00005', '1997-11-15', 'Pria', '2024-05-05 06:20:00'),
('PLP00006', '2002-02-25', 'Wanita', '2024-05-06 05:45:00'),
('PLP00007', '1996-04-10', 'Pria', '2024-05-07 01:15:00'),
('PLP00008', '2001-09-21', 'Wanita', '2024-05-08 02:10:00'),
('PLP00009', '1999-01-18', 'Wanita', '2024-05-09 01:55:00'),
('PLP00010', '1998-06-06', 'Wanita', '2024-05-10 00:00:00'),
('PLP00011', '1997-12-22', 'Wanita', '2024-05-11 04:00:00'),
('PLP000111', '2012-12-12', 'Pria', '2025-10-30 17:07:17'),
('PLP00012', '1995-05-13', 'Wanita', '2024-05-12 07:40:00'),
('PLP00013', '2000-01-28', 'Wanita', '2024-05-13 01:30:00'),
('PLP00014', '1998-09-02', 'Wanita', '2024-05-14 03:45:00'),
('PLP00015', '2003-02-18', 'Wanita', '2024-05-15 02:20:00'),
('PLP00016', '1999-07-08', 'Wanita', '2024-05-16 00:50:00'),
('PLP00017', '1996-03-11', 'Pria', '2024-05-17 01:00:00'),
('PLP00018', '1997-11-12', 'Wanita', '2024-05-18 06:10:00'),
('PLP00019', '1999-10-10', 'Pria', '2024-05-19 08:00:00'),
('PLP00020', '2002-04-09', 'Wanita', '2024-05-19 23:45:00'),
('PLP00021', '1998-08-08', 'Pria', '2024-05-21 05:20:00'),
('PLP00022', '1997-03-30', 'Wanita', '2024-05-22 01:35:00'),
('PLP00023', '1996-12-11', 'Pria', '2024-05-23 03:15:00'),
('PLP00024', '1998-02-05', 'Wanita', '2024-05-24 02:00:00'),
('PLP00025', '1995-11-07', 'Pria', '2024-05-25 00:10:00'),
('PLP00026', '2001-03-14', 'Wanita', '2024-05-26 01:25:00'),
('PLP00027', '1999-01-16', 'Pria', '2024-05-27 03:00:00'),
('PLP00028', '1996-07-09', 'Wanita', '2024-05-28 04:20:00'),
('PLP00029', '1998-10-20', 'Pria', '2024-05-29 02:45:00'),
('PLP00030', '2000-06-15', 'Wanita', '2024-05-30 06:55:00'),
('PLP00031', '1997-04-03', 'Wanita', '2024-05-31 02:40:00'),
('PLP00032', '1996-02-14', 'Wanita', '2024-06-01 01:05:00'),
('PLP00033', '1998-08-26', 'Pria', '2024-06-02 03:30:00'),
('PLP00034', '1999-12-09', 'Wanita', '2024-06-03 00:25:00'),
('PLP00035', '1995-05-22', 'Pria', '2024-06-04 07:10:00'),
('PLP00036', '1997-09-29', 'Wanita', '2024-06-05 02:55:00'),
('PLP00037', '1998-07-17', 'Pria', '2024-06-06 05:30:00'),
('PLP00038', '1999-11-14', 'Wanita', '2024-06-07 00:50:00'),
('PLP00039', '1995-06-12', 'Pria', '2024-06-08 01:40:00'),
('PLP00040', '1997-01-23', 'Wanita', '2024-06-09 00:15:00'),
('PLP00041', '1998-03-11', 'Pria', '2024-06-10 02:25:00'),
('PLP00042', '1999-05-27', 'Wanita', '2024-06-11 03:10:00'),
('PLP00043', '1997-02-16', 'Pria', '2024-06-12 01:50:00'),
('PLP00044', '1996-08-21', 'Wanita', '2024-06-13 00:45:00'),
('PLP00045', '1998-12-04', 'Pria', '2024-06-14 05:35:00'),
('PLP00046', '1999-09-13', 'Wanita', '2024-06-15 02:05:00'),
('PLP00047', '1997-06-02', 'Pria', '2024-06-16 06:50:00'),
('PLP00048', '1995-01-25', 'Wanita', '2024-06-17 00:00:00'),
('PLP00049', '1996-10-30', 'Pria', '2024-06-18 04:30:00'),
('PLP00050', '2001-12-19', 'Wanita', '2024-06-19 01:20:00'),
('PLP00051', '2000-12-12', 'Wanita', '2025-11-02 05:46:13'),
('PLP00052', '1999-06-27', 'Pria', '2025-11-02 05:49:33'),
('PLP00053', '2023-12-12', 'Pria', '2025-11-02 08:14:45'),
('PLP00054', '2020-12-12', 'Wanita', '2025-11-02 09:14:01'),
('PLP00055', '2005-12-12', 'Wanita', '2025-11-02 09:14:39'),
('PLP00056', '2005-12-12', 'Wanita', '2025-11-02 09:15:26'),
('PLP00057', '2005-12-12', 'Wanita', '2025-11-02 09:16:05'),
('PLP00058', '2021-11-10', 'Pria', '2025-11-02 11:05:57'),
('PLP00059', '2017-11-08', 'Wanita', '2025-11-02 11:08:30'),
('PLP00060', '1017-09-18', 'Pria', '2025-11-02 23:36:26'),
('PLP00061', '2025-05-07', 'Pria', '2025-11-03 01:51:52'),
('PLP00062', '2006-12-21', 'Pria', '2025-11-04 08:27:30'),
('PLP00063', '2025-08-07', 'Wanita', '2025-11-04 12:15:19');

-- --------------------------------------------------------

--
-- Table structure for table `tanggapan`
--

CREATE TABLE `tanggapan` (
  `id_tanggapan` varchar(30) NOT NULL,
  `isi_tanggapan` varchar(255) NOT NULL,
  `status` enum('Dalam_Proses','Diterima','Selesai','Ditolak') NOT NULL,
  `tanggal_tanggapan` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `id_laporan` varchar(30) NOT NULL,
  `id_user_admin` varchar(30) DEFAULT NULL,
  `id_lembaga` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tanggapan`
--

INSERT INTO `tanggapan` (`id_tanggapan`, `isi_tanggapan`, `status`, `tanggal_tanggapan`, `id_laporan`, `id_user_admin`, `id_lembaga`) VALUES
('TG1762258383340', 'oh jangan di tahan tahan', 'Dalam_Proses', '2025-11-04 12:13:03', 'LAP011', 'USR001', 'L001'),
('TGP001', 'Laporan telah diterima dan sedang diverifikasi oleh tim hukum internal.', 'Diterima', '2024-07-02 07:00:00', 'LAP001', 'USR001', 'L001'),
('TGP002', 'Laporan indikasi korupsi telah diteruskan ke KPK untuk investigasi lebih lanjut.', 'Selesai', '2024-07-04 02:00:00', 'LAP002', 'USR002', 'L002'),
('TGP003', 'Bukti laporan tidak lengkap. Mohon lengkapi data pendukung agar dapat diproses kembali.', 'Ditolak', '2024-07-06 01:30:00', 'LAP004', 'USR004', 'L003'),
('TGP006', 'Laporan pelanggaran etika sedang diverifikasi oleh lembaga pengawas.', 'Dalam_Proses', '2024-07-18 06:45:00', 'LAP006', 'USR006', 'L003');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` varchar(30) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `username`, `password`, `email`) VALUES
('PLP00001', 'Ahmad Fauzan', 'ahmadf', 'pelapor123', 'ahmadf@gmail.com'),
('PLP000011', 'chiqo', 'chiqo21', 'chiqo', '121'),
('PLP00002', 'Dewi Lestari', 'dewiles', 'pelapor123', 'dewiles@gmail.com'),
('PLP00003', 'Rizky Pratama', 'rizky', 'pelapor123', 'rizky@gmail.com'),
('PLP00004', 'Intan Sari', 'intans', 'pelapor123', 'intans@gmail.com'),
('PLP00005', 'M. Fadhil', 'fadhil', 'pelapor123', 'fadhil@gmail.com'),
('PLP00006', 'Nur Aisyah', 'aisyah', 'pelapor123', 'aisyah@gmail.com'),
('PLP00007', 'Rahmat Hidayat', 'rahmat', 'pelapor123', 'rahmat@gmail.com'),
('PLP00008', 'Salsa Amalia', 'salsa', 'pelapor123', 'salsa@gmail.com'),
('PLP00009', 'Dian Puspita', 'dianp', 'pelapor123', 'dianp@gmail.com'),
('PLP00010', 'Yusuf Maulana', 'yusuf', 'pelapor123', 'yusuf@gmail.com'),
('PLP00011', 'Rina Handayani', 'rinah', 'pelapor123', 'rinah@gmail.com'),
('PLP000111', 'chiiii', 'chiiii', 'chiiii', 'chiiii'),
('PLP00012', 'Bayu Saputra', 'bayu', 'pelapor123', 'bayu@gmail.com'),
('PLP00013', 'Tiara Permata', 'tiara', 'pelapor123', 'tiara@gmail.com'),
('PLP00014', 'Hendra Gunawan', 'hendra', 'pelapor123', 'hendra@gmail.com'),
('PLP00015', 'Mega Putri', 'megap', 'pelapor123', 'megap@gmail.com'),
('PLP00016', 'Eka Susanti', 'ekasus', 'pelapor123', 'ekasus@gmail.com'),
('PLP00017', 'Fajar Ramadhan', 'fajarr', 'pelapor123', 'fajarr@gmail.com'),
('PLP00018', 'Lina Marlina', 'linam', 'pelapor123', 'linam@gmail.com'),
('PLP00019', 'Taufik Nugraha', 'taufik', 'pelapor123', 'taufik@gmail.com'),
('PLP00020', 'Nabila Shafa', 'nabila', 'pelapor123', 'nabila@gmail.com'),
('PLP00021', 'Joko Santoso', 'jokos', 'pelapor123', 'jokos@gmail.com'),
('PLP00022', 'Putri Ayu', 'putria', 'pelapor123', 'putria@gmail.com'),
('PLP00023', 'Alfian Ridho', 'alfian', 'pelapor123', 'alfian@gmail.com'),
('PLP00024', 'Maya Anjani', 'maya', 'pelapor123', 'maya@gmail.com'),
('PLP00025', 'Fikri Ramzi', 'fikri', 'pelapor123', 'fikri@gmail.com'),
('PLP00026', 'Citra Larasati', 'citra', 'pelapor123', 'citra@gmail.com'),
('PLP00027', 'Rafli Aditya', 'rafli', 'pelapor123', 'rafli@gmail.com'),
('PLP00028', 'Winda Pertiwi', 'winda', 'pelapor123', 'winda@gmail.com'),
('PLP00029', 'Andra Kurniawan', 'andra', 'pelapor123', 'andra@gmail.com'),
('PLP00030', 'Sinta Dewanti', 'sinta', 'pelapor123', 'sinta@gmail.com'),
('PLP00031', 'Hana Oktaviani', 'hana', 'pelapor123', 'hana@gmail.com'),
('PLP00032', 'Rafi Maulana', 'rafim', 'pelapor123', 'rafim@gmail.com'),
('PLP00033', 'Yuni Astuti', 'yuni', 'pelapor123', 'yuni@gmail.com'),
('PLP00034', 'Aldi Prasetya', 'aldi', 'pelapor123', 'aldi@gmail.com'),
('PLP00035', 'Naufal Zaki', 'naufal', 'pelapor123', 'naufal@gmail.com'),
('PLP00036', 'Laila Rahma', 'laila', 'pelapor123', 'laila@gmail.com'),
('PLP00037', 'Rico Saputra', 'rico', 'pelapor123', 'rico@gmail.com'),
('PLP00038', 'Aulia Fitri', 'aulia', 'pelapor123', 'aulia@gmail.com'),
('PLP00039', 'Bagus Nugroho', 'bagus', 'pelapor123', 'bagus@gmail.com'),
('PLP00040', 'Tia Cahyani', 'tiac', 'pelapor123', 'tiac@gmail.com'),
('PLP00041', 'Yoga Pradana', 'yoga', 'pelapor123', 'yoga@gmail.com'),
('PLP00042', 'Melati Anggraini', 'melati', 'pelapor123', 'melati@gmail.com'),
('PLP00043', 'Reza Alfarizi', 'reza', 'pelapor123', 'reza@gmail.com'),
('PLP00044', 'Sari Melinda', 'sarim', 'pelapor123', 'sarim@gmail.com'),
('PLP00045', 'Iqbal Hakim', 'iqbal', 'pelapor123', 'iqbal@gmail.com'),
('PLP00046', 'Farida Nur', 'farida', 'pelapor123', 'farida@gmail.com'),
('PLP00047', 'Bayu Firmansyah', 'bayuf', 'pelapor123', 'bayuf@gmail.com'),
('PLP00048', 'Desi Kurniasih', 'desi', 'pelapor123', 'desi@gmail.com'),
('PLP00049', 'Ilham Prakoso', 'ilham', 'pelapor123', 'ilham@gmail.com'),
('PLP00050', 'Tasya Rahmadani', 'tasya', 'pelapor123', 'tasya@gmail.com'),
('PLP00051', 'Nadilaaa', 'nadilaaa', 'abc123', 'a'),
('PLP00052', 'karsein', 'karsein', 'nana123', 'sein@gmail.com'),
('PLP00053', 'Udin', 'udingagah', '123', 'udin'),
('PLP00054', 'Riwu', 'efae', '123', 'Riwu@gmail.com'),
('PLP00055', 'riwu', 'riwu', '123', 'rini@email.com'),
('PLP00056', 'rini', 'afaw', '123', 'rini@dwi.com'),
('PLP00057', 'rini', 'rini', '123', 'rini@yahoo'),
('PLP00058', 'asdasasd', 'asda', '123', 'asdds@gmail.com'),
('PLP00059', 'ureger', 'ERFE', '123', 'ruvfh@gmail.com'),
('PLP00060', 'panasea', 'panasea', 'panasea', 'panasea@mama.com'),
('PLP00061', 'joko sembung', 'menemui', 'aku', 'apalah@gmail.com'),
('PLP00062', 'telenovia', 'realityclub', '123', 'telenovia@gmail.com'),
('PLP00063', 'ku tak pulang pulang', 'chiqooo', '123', 'chiqo@gmail.com'),
('USR001', 'Chiqo Nanda Rial Pratama', 'chiqo', 'admin123', 'chiqo@gmail.com'),
('USR002', 'Rini Wulandari', 'riniw', 'admin123', 'rini@gmail.com'),
('USR003', 'Isrina Lutfiah', 'isrina', 'admin123', 'isrina@gmail.com'),
('USR004', 'Nadila Putri', 'nadila', 'admin123', 'nadila@gmail.com'),
('USR005', 'Andi Prasetyo', 'andi', 'admin123', 'andi@gmail.com'),
('USR006', 'Budi Santoso', 'budi', 'admin123', 'budi@gmail.com'),
('USR007', 'Siti Rahmawati', 'siti', 'admin123', 'siti@gmail.com'),
('USR008', 'Dimas Putra', 'dimas', 'admin123', 'dimas@gmail.com'),
('USR009', 'Farhan Syahputra', 'farhan', 'admin123', 'farhan@gmail.com'),
('USR010', 'Lia Kartika', 'lia', 'admin123', 'lia@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `laporan`
--
ALTER TABLE `laporan`
  ADD PRIMARY KEY (`id_laporan`),
  ADD KEY `id_user_pelapor` (`id_user_pelapor`);

--
-- Indexes for table `lembaga`
--
ALTER TABLE `lembaga`
  ADD PRIMARY KEY (`id_lembaga`),
  ADD KEY `FK95tqjm89xwo5s28a2tkxc77tu` (`id_user`);

--
-- Indexes for table `pelapor`
--
ALTER TABLE `pelapor`
  ADD PRIMARY KEY (`id_user`);

--
-- Indexes for table `tanggapan`
--
ALTER TABLE `tanggapan`
  ADD PRIMARY KEY (`id_tanggapan`),
  ADD KEY `id_laporan` (`id_laporan`),
  ADD KEY `id_user_admin` (`id_user_admin`),
  ADD KEY `id_lembaga` (`id_lembaga`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `username_2` (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `laporan`
--
ALTER TABLE `laporan`
  ADD CONSTRAINT `FKfuyah2sgvp6nfhut8pc1b67g7` FOREIGN KEY (`id_user_pelapor`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `laporan_ibfk_1` FOREIGN KEY (`id_user_pelapor`) REFERENCES `pelapor` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `lembaga`
--
ALTER TABLE `lembaga`
  ADD CONSTRAINT `FK95tqjm89xwo5s28a2tkxc77tu` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`);

--
-- Constraints for table `pelapor`
--
ALTER TABLE `pelapor`
  ADD CONSTRAINT `pelapor_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tanggapan`
--
ALTER TABLE `tanggapan`
  ADD CONSTRAINT `tanggapan_ibfk_1` FOREIGN KEY (`id_laporan`) REFERENCES `laporan` (`id_laporan`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tanggapan_ibfk_2` FOREIGN KEY (`id_user_admin`) REFERENCES `admin` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tanggapan_ibfk_3` FOREIGN KEY (`id_lembaga`) REFERENCES `lembaga` (`id_lembaga`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
