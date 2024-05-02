package org.example.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class User{
    private Integer id;
    private String user;
    private String password;
    private String name;
    private String surname;
    private String email;
    private LocalDate born_date;
    private String image;

    public User(String user, String password, String name, String surname, String email, LocalDate born_date, String image) {
        this.user = user;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.born_date = born_date;
        this.image = image;
    }

    public User(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBorn_date() {
        return born_date;
    }

    public void setBorn_date(LocalDate born_date) {
        this.born_date = born_date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(user, user.user) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", born_date=" + born_date +
                ", image='" + image + '\'' +
                '}';
    }
}
