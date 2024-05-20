package org.example.model.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a yser entity that extends into modeler and client.
 */
public class User {
    private Integer id;
    private String user;
    private String password;
    private String name;
    private String surname;
    private String email;
    private LocalDate bornDate;
    private byte[] image;

    /**
     * Constructs a Modeler with the specified attributes.
     *
     * @param user     the username of the user
     * @param password the password of the user
     * @param name     the name of the user
     * @param surname  the surname of the user
     * @param email    the email address of the user
     * @param bornDate the date of birth of the user
     * @param image    the profile image of the user
     */
    public User(String user, String password, String name, String surname, String email, LocalDate bornDate, byte[] image) {
        this.user = user;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bornDate = bornDate;
        this.image = image;
    }

    /**
     * Default constructor for User.
     */
    public User() {
    }

    /**
     * Returns the ID of the user.
     *
     * @return the ID of the user
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID of the user
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the User of the user.
     *
     * @return the User of the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user of the user.
     *
     * @param user the user of the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the Password of the user.
     *
     * @return the user of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the Password of the user.
     *
     * @param password the Password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the Name of the user.
     *
     * @return the Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Name of the user.
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the Surname of the user.
     *
     * @return the Surname of the user
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the Surname of the user.
     *
     * @param surname the Surname of the user
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the Email of the user.
     *
     * @return the Email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the Email of the user.
     *
     * @param email the Email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the Born Date of the user.
     *
     * @return the Born Date of the user
     */
    public LocalDate getBornDate() {
        return bornDate;
    }

    /**
     * Sets the Born Date of the user.
     *
     * @param bornDate the Born Date of the user
     */
    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }

    /**
     * Returns the Image of the user.
     *
     * @return the Image of the user
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the Imagw of the user.
     *
     * @param image the Imageee of the user
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare
     * @return true if this object is the same as the object argument; false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(user, user.user) && Objects.equals(password, user.password);
    }

    /**
     * Returns a hash code value for the user.
     *
     * @return a hash code value for the user
     */
    @Override
    public int hashCode() {
        return Objects.hash(user, password);
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", bornDate=" + bornDate +
                ", image='" + image + '\'' +
                '}';
    }
}
