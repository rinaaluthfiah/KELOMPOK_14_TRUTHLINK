/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.Lembaga;
import java.util.List;

public interface LembagaIDao {
    void save(Lembaga lembaga);
    Lembaga getById(String id);
    List<Lembaga> getAll();
    void update(Lembaga lembaga);
    void delete(Lembaga lembaga);

    long countByJenis(String jenis);

}

