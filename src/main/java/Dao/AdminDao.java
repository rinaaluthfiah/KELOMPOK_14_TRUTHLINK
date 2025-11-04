/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.Admin;
import java.util.List;

public interface AdminDao {
    void save(Admin admin);
    Admin getById(String id);
    List<Admin> getAll();
    void update(Admin admin);
    void delete(Admin admin);
}