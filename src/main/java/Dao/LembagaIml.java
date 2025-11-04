/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.Lembaga;
import com.mycompany.linkkk.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LembagaIml implements  LembagaIDao{

    @Override
    public void save(Lembaga lembaga) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(lembaga);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Lembaga getById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Lembaga.class, id);
        }
    }

    @Override
    public List<Lembaga> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Lembaga", Lembaga.class).list();
        }
    }

    @Override
    public void update(Lembaga lembaga) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(lembaga);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Lembaga lembaga) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(lembaga);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }
    
     @Override
    public long countByJenis(String jenis) {
        long count = 0;
        try (org.hibernate.Session session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(l) FROM Lembaga l WHERE l.jenis_lembaga = :jenis";
            var query = session.createQuery(hql, Long.class);

            // karena jenis_lembaga adalah enum, kita konversi String ke enum:
            query.setParameter("jenis", entity.Lembaga.JenisLembaga.valueOf(jenis));

            count = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}


