package model;

import com.fasterxml.jackson.annotation.*;

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
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id")
    )
    @JsonIgnore
    private Set<BookCase> bookCases;


    public Set<BookCase> getBookCases() {
        return bookCases;
    }

    public void setBookCases(Set<BookCase> bookCases) {
        this.bookCases = bookCases;
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
    public String toString() {
        return "Shelf{" +
                "name='" + name + '\'' +
                '}';
    }
}
