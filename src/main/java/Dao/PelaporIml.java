package Dao;

import entity.Pelapor;
import com.mycompany.linkkk.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class PelaporIml implements PelaporDao {

    @Override
    public void save(Pelapor pelapor) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            session.persist(pelapor); // ✅ pakai persist() biar aman di Hibernate 6
            tx.commit();

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close(); // ✅ tutup manual setelah commit
            }
        }
    }

    @Override
    public Pelapor getById(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Pelapor p = session.get(Pelapor.class, id);
        session.close();
        return p;
    }

    @Override
    public List<Pelapor> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Pelapor> list = session.createQuery("from Pelapor", Pelapor.class).list();
        session.close();
        return list;
    }

    @Override
    public void update(Pelapor pelapor) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.merge(pelapor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void delete(Pelapor pelapor) {
        Transaction tx = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.remove(pelapor);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
