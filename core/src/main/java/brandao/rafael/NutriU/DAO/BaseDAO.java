package brandao.rafael.NutriU.DAO;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Slf4j
public abstract class BaseDAO<T> {

    private final SessionFactory sessionFactory;

    public BaseDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session openSessionWithTransaction(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        return session;
    }

    protected Session openSession(){
        return sessionFactory.getCurrentSession();
    }

    protected boolean closeSessionWithTransaction(Session session) {
        boolean successful = false;
        try {
            session.getTransaction().commit();
            successful = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            session.close();
        }
        return successful;
    }

    protected void closeSession(Session session){
        session.close();
    }

    public List<T> loadAllData(Class<T> type) {
        Session session = openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        criteria.from(type);
        List<T> list = session.createQuery(criteria).getResultList();
        closeSession(session);
        return list;
    }

    public T findById(Class<T> entityType, int id){
        Session session = openSession();
        T object;
        try{
            object = session.get(entityType, id);
        } catch  (Exception e){
            log.error(e.getMessage());
            closeSession(session);
            return null;
        }
        closeSession(session);
        return object;
    }

    public boolean save(Object entity){
        Session session = openSessionWithTransaction();
        try{
            session.save(entity);
        } catch  (Exception e){
            log.error(e.getMessage());
            closeSession(session);
            return false;
        }
        return closeSessionWithTransaction(session);
    }

    public boolean delete(Class<T> type, int id){
        Session session = openSessionWithTransaction();
        try{
            log.info(this.getClass().getName());
            session.delete(session.get(type, id));
        } catch  (Exception e){
            log.error(e.getMessage());
            closeSession(session);
            return false;
        }
        return closeSessionWithTransaction(session);
    }

    public boolean update(Object entity){
        Session session = openSessionWithTransaction();
        try{
            session.update(entity);
        } catch  (Exception e){
            log.error(e.getMessage());
            closeSession(session);
            return false;
        }
        return closeSessionWithTransaction(session);
    }
}
