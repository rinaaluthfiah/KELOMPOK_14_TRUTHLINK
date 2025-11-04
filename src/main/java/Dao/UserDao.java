/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.User;
import java.util.List;

public interface UserDao {
    void save(User user);
    User getById(String id);
    List<User> getAll();
    void update(User user);
    void delete(User user);

    public long countAllUsers();

    
}
