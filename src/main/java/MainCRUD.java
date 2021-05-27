
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class MainCRUD {

    protected static void createObjectInDB(Object o, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(o);
        transaction.commit();
        em.close();
    }

    public static void deleteBookCopy(BookCopy bookCopy, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(bookCopy) ? bookCopy : em.merge(bookCopy));
        transaction.commit();
        em.close();
    }

    public static void updateBookCopy(BookCopy bookCopy, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(bookCopy);
        transaction.commit();
        em.close();
    }
}