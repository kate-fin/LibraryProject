import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

public class MainQuery {

    protected static Author getAuthor(Author author, Boolean isCreateIfNotExist, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> criteria = cb.createQuery(Author.class);
        Root<Author> root = criteria.from(Author.class);
        criteria
                .select(root);
        List<Author> data = em.createQuery(criteria)
                .getResultList();
        for (Author d : data) {
            if (author.equals(d)) {
                return d;
            }
        }
        if (isCreateIfNotExist) {
            MainCRUD.createObjectInDB(author, managerFactory);
            return author;
        }
        return null;
    }

    protected static Book getBook(Book book, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
        Root<Book> root = criteria.from(Book.class);
        criteria
                .select(root);
        List<Book> data = em.createQuery(criteria)
                .getResultList();
        for (Book d : data) {
            if (book.equals(d)) {
                return d;
            }
        }
        MainCRUD.createObjectInDB(book, managerFactory);
        return book;
    }

    private static boolean isBookCase(BookCase bookCase, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookCase> criteria = cb.createQuery(BookCase.class);
        Root<BookCase> root = criteria.from(BookCase.class);
        criteria
                .select(root);
        List<BookCase> data = em.createQuery(criteria)
                .getResultList();
        for (BookCase d : data) {
            if (bookCase.equalsName(d)) {
                return true;
            }
        }
        return false;
    }

    protected static BookCase getBookCase(BookCase bookCase, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookCase> criteria = cb.createQuery(BookCase.class);
        Root<BookCase> root = criteria.from(BookCase.class);
        criteria
                .select(root);
        List<BookCase> data = em.createQuery(criteria)
                .getResultList();
        for (BookCase d : data) {
            if (bookCase.equalsName(d)) {
                if (!bookCase.equals(d)) {
                    Set<Shelf> shelves = bookCase.getShelves();
                    for (Shelf s : shelves) {
                        if (!isShelf(s, managerFactory)) {
                            s.setBookCase(Set.of(d));
                            MainCRUD.createObjectInDB(s, managerFactory);
                        }
                    }
                }
                return d;
            }
        }
        MainCRUD.createObjectInDB(bookCase, managerFactory);
        return bookCase;
    }

    protected static Genre getGenre(Genre genre, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Genre> criteria = cb.createQuery(Genre.class);
        Root<Genre> root = criteria.from(Genre.class);
        criteria
                .select(root);
        List<Genre> data = em.createQuery(criteria)
                .getResultList();
        for (Genre d : data) {
            if (genre.equals(d)) {
                return d;
            }
        }
        MainCRUD.createObjectInDB(genre, managerFactory);
        return genre;
    }

    protected static Publisher getPublisher(Publisher publisher, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Publisher> criteria = cb.createQuery(Publisher.class);
        Root<Publisher> root = criteria.from(Publisher.class);
        criteria
                .select(root);
        List<Publisher> data = em.createQuery(criteria)
                .getResultList();
        for (Publisher d : data) {
            if (publisher.equals(d)) {
                return d;
            }
        }
        MainCRUD.createObjectInDB(publisher, managerFactory);
        return publisher;
    }

    protected static Boolean isReader(Reader reader, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Reader> criteria = cb.createQuery(Reader.class);
        Root<Reader> readerRoot = criteria.from(Reader.class);
        criteria
                .select(readerRoot);
       List<Reader> data = em.createQuery(criteria)
                .getResultList();
        for (Reader d : data) {
            if (reader.getLogin().equals(d.getLogin()) && reader.getPassword().equals(d.getPassword())) {
                return true;
            }
        }
        return false;
    }


    public static Boolean isShelf(Shelf shelf, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Shelf> criteria = cb.createQuery(Shelf.class);
        Root<Shelf> root = criteria.from(Shelf.class);
        criteria
                .select(root);
        List<Shelf> data = em.createQuery(criteria)
                .getResultList();
        for (Shelf d : data) {
            if (shelf.equalsName(d)) {
                return true;
            }
        }
        return false;
    }

    public static Shelf getShelf(Shelf shelf, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Shelf> criteria = cb.createQuery(Shelf.class);
        Root<Shelf> root = criteria.from(Shelf.class);
        criteria.select(root);
        List<Shelf> data = em.createQuery(criteria)
                .getResultList();
        for (Shelf d : data) {
            if (shelf.equalsName(d)) {
                if (!shelf.equals(d)) {
                    Set<BookCase> bookCases = shelf.getBookCases();
                    for (BookCase b : bookCases) {
                        if (!isBookCase(b, managerFactory)) {
                            b.setShelves(Set.of(d));
                            MainCRUD.createObjectInDB(b, managerFactory);
                        }
                    }
                }
                return d;
            }
        }
        MainCRUD.createObjectInDB(shelf, managerFactory);
        return shelf;
    }


    public static List<BookCopy> getAllBookCopy(EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookCopy> criteria = cb.createQuery(BookCopy.class);
        Root<BookCopy> root = criteria.from(BookCopy.class);
        criteria
                .select(root)
                .orderBy(cb.asc(root.get("book").get("author").get("surname")), cb.asc(root.get("book").get("name")));
        return em.createQuery(criteria)
                .getResultList();
    }

    public static List<BookCopy> getBookCopy(Book book, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookCopy> criteria = cb.createQuery(BookCopy.class);
        Root<BookCopy> root = criteria.from(BookCopy.class);
        Predicate predicateForBookName = cb.equal(root.get("book").get("name"), book.getName());
        Predicate predicateForBookAuthor = cb.equal(root.get("book").get("author"), book.getAuthor());
        criteria
                .select(root)
                .where(cb.and(predicateForBookName, predicateForBookAuthor))
                .orderBy(cb.asc(root.get("book").get("author").get("surname")), cb.asc(root.get("book").get("name")));
        return em.createQuery(criteria)
                .getResultList();
    }

    public static List<BookCopy> getBookCopy(BookCase bookCase, Shelf shelf, EntityManagerFactory managerFactory) {
        EntityManager em = managerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<BookCopy> criteria = cb.createQuery(BookCopy.class);
        Root<BookCopy> root = criteria.from(BookCopy.class);
        Predicate predicateForBookCase = cb.equal(root.get("bookCase").get("name"), bookCase.getName());
        Predicate predicateForShelf = cb.equal(root.get("shelf").get("name"), shelf.getName());
        criteria
                .select(root)
                .where(cb.and(predicateForBookCase, predicateForShelf))
                .orderBy(cb.asc(root.get("book").get("author").get("surname")), cb.asc(root.get("book").get("name")));
        return em.createQuery(criteria)
                .getResultList();
    }
}
