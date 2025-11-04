/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */

import entity.Tanggapan;
import java.util.List;

public interface TanggapanDao {
    void save(Tanggapan tanggapan);
    Tanggapan getById(String id);
    List<Tanggapan> getAll();
    void update(Tanggapan tanggapan);
    void delete(Tanggapan tanggapan);

    public long countByStatus(String diterima);
}
