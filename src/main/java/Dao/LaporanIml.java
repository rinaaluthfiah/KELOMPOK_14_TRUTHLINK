/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author USER
*/
import entity.Laporan;
import com.mycompany.linkkk.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LaporanIml implements LaporanDao {

    @Override
    public void save(Laporan laporan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(laporan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Laporan getById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Laporan.class, id);
        }
    }

    @Override
    public List<Laporan> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Laporan", Laporan.class).list();
        }
    }

    @Override
    public void update(Laporan laporan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(laporan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Laporan laporan) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(laporan);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

        public long countAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(l) FROM Laporan l";
            return session.createQuery(hql, Long.class).uniqueResult();
        }
    }
    
    @Override
    public String getMostFrequentKategori() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT l.kategori, COUNT(l.kategori) " +
                     "FROM Laporan l " +
                     "GROUP BY l.kategori " +
                     "ORDER BY COUNT(l.kategori) DESC";
            var query = session.createQuery(hql, Object[].class);
            query.setMaxResults(1);
            Object[] result = query.uniqueResult();
            return result != null ? (String) result[0] : "Tidak ada data";
        }
    }
}

