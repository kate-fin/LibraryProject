package model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@DiscriminatorValue("reader")
@Entity
public class Reader extends Person {

    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "Person=" + this.getName() + this.getPatronymic() + this.getSurname() +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
