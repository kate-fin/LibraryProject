package model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@DiscriminatorValue("author")
@Entity
public class Author extends Person{
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(this.getName(), author.getName()) &&
                Objects.equals(this.getPatronymic(), author.getPatronymic()) &&
                        Objects.equals(this.getSurname(), author.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "Author{" +
//                 this.getPersonClass().toString()+
                ", name='" + getName() + '\'' +
                ", surname='" + getSurname() + '\'' +
                ", patronymic='" + getPatronymic() + '\'' +
                '}';
    }
}
