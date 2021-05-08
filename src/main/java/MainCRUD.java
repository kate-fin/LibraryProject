
import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainCRUD {

    private static final EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("library");
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {

    }

    protected static void createObjectInDB(Object o) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(o);
        transaction.commit();
        em.close();
    }

    protected static void createBook(Book _book) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Book book = MainQuery.getBook(_book);
        if (book == null){
            transaction.begin();
            em.persist(_book);
            transaction.commit();
            em.close();
        }
        else{
//            List<BookCopy> bookCopies = _book.getBookCopies();
//            for(BookCopy copy: bookCopies){
//                copy.setBook(book);
//            }
//            creatBookCopies(bookCopies);
        }

    }

    protected static void creatBookCopies(List<BookCopy> bookCopies){
        for(BookCopy copy: bookCopies){
            EntityManager em1 = managerFactory.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            em1.persist(copy);
            transaction1.commit();
            em1.close();
        }

    }

    public static void deleteBookCopy(BookCopy bookCopy) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
//        em.remove(bookCopy);
        em.remove(em.contains(bookCopy) ? bookCopy : em.merge(bookCopy));
        transaction.commit();
        em.close();
    }

    public static void updateBookCopy(BookCopy bookCopy) {
        EntityManager em = managerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(bookCopy);
        transaction.commit();
        em.close();
    }
}