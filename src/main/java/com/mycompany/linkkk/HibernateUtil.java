/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.HibernateUtil to edit this template
 */

package com.mycompany.linkkk;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            
            // Tambahkan entitas secara eksplisit (penting di Hibernate 6)
            configuration.addAnnotatedClass(entity.User.class);
            configuration.addAnnotatedClass(entity.Admin.class);
            configuration.addAnnotatedClass(entity.Pelapor.class);
            configuration.addAnnotatedClass(entity.Laporan.class);
            configuration.addAnnotatedClass(entity.Tanggapan.class);
            configuration.addAnnotatedClass(entity.Lembaga.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("✅ Hibernate session factory created successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + e);
        }
    }
    return sessionFactory;
}
}


