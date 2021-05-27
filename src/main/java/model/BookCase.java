package model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class BookCase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    //FK Shelf
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bookCases")//, cascade = CascadeType.MERGE)
    @JsonIgnore
    private Set<Shelf> shelves;

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

    public Set<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(Set<Shelf> shelves) {
        this.shelves = shelves;
    }

    public boolean equalsName(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCase bookCase = (BookCase) o;
        return this.name.equals(bookCase.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCase bookCase = (BookCase) o;
        for(Shelf shelf: this.shelves){
            for(Shelf shelfOfBookCase: bookCase.getShelves()){
                if(shelf.getName().equals(shelfOfBookCase.getName())){
                    return Objects.equals(this.name, bookCase.name);
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "BookCase{" +
                "name='" + name + '\'' +
                '}';
    }
}
