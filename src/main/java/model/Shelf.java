package model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    //FK to BookCase
    @ManyToMany//(cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id")
    )
    private Set<BookCase> bookCases;

    public Set<BookCase> getBookCases() {
        return bookCases;
    }

    public void setBookCases(Set<BookCase> bookCases) {
        this.bookCases = bookCases;
    }

    public Set<BookCase> getBookCase() {
        return bookCases;
    }

    public void setBookCase(Set<BookCase> bookCase) {
        this.bookCases = bookCase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equalsName(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        return shelf.name.equals(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelf shelf = (Shelf) o;
        for(BookCase bookCase: this.bookCases){
            for(BookCase bookCaseOfShelf: shelf.getBookCase()){
                if(bookCase.getName().equals(bookCaseOfShelf.getName())){
                    return Objects.equals(this.name, shelf.name);
                }
            }
        }
        return false;
//        return Objects.equals(name, shelf.name) &&
//                bookCases.stream().anyMatch(bookCase -> bookCase.getName().equals(shelf.getBookCase().getClass().getName()));
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(name, bookCases);
//    }


    @Override
    public String toString() {
        return "Shelf{" +
                "name='" + name + '\'' +
                '}';
    }
}
