/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.Pelapor;
import java.util.List;

public interface PelaporDao {
    void save(Pelapor pelapor);
    Pelapor getById(String id);
    List<Pelapor> getAll();
    void update(Pelapor pelapor);
    void delete(Pelapor pelapor);
}