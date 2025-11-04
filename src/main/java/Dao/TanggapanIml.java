/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
 */
import entity.Tanggapan;
import com.mycompany.linkkk.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class TanggapanIml implements TanggapanDao {

    @Override
    public void save(Tanggapan tanggapan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(tanggapan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Tanggapan getById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Tanggapan.class, id);
        }
    }

    @Override
    public List<Tanggapan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tanggapan", Tanggapan.class).list();
        }
    }

    @Override
    public void update(Tanggapan tanggapan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(tanggapan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tanggapan tanggapan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(tanggapan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

@Override
    public long countByStatus(String status) {
        long count = 0;
        try (org.hibernate.Session session = com.mycompany.linkkk.HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(t) FROM Tanggapan t WHERE t.status = :status";
            var query = session.createQuery(hql, Long.class);
            
            query.setParameter("status", entity.Tanggapan.Status.valueOf(status));

            count = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public List<Tanggapan> getByStatus(entity.Tanggapan.Status status) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Tanggapan where status = :status";
            return session.createQuery(hql, entity.Tanggapan.class)
                          .setParameter("status", status)
                          .list();
        } catch (Exception e) {
            e.printStackTrace();
            return java.util.Collections.emptyList();
        }
    }

}
