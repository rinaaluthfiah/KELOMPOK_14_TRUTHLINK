/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.linkkk;

import org.hibernate.Session;

/**
 *
 * @author USER
 */
public class TestHibernate {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Hibernate berhasil terhubung!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
