package model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //FK to Shelf
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(nullable = false)
    private Shelf shelf;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(nullable = false)
    private BookCase bookCase;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public BookCase getBookCase() {
        return bookCase;
    }

    public void setBookCase(BookCase bookCase) {
        this.bookCase = bookCase;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "shelf=" + shelf.toString() +
                "bookCase=" + bookCase.toString() +
                ", book=" + book.toString() +
                '}';
    }
}
