/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
 
import entity.Laporan;
import java.util.List;

public interface LaporanDao {
    void save(Laporan laporan);
    Laporan getById(String id);
    List<Laporan> getAll();
    void update(Laporan laporan);
    void delete(Laporan laporan);

    public long countAll();
    public String getMostFrequentKategori();
}
   

