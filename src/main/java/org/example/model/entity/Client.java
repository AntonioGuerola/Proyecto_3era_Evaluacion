package org.example.model.entity;

import java.time.LocalDate;

/**
 * Represents a client entity that.
 */
public class Client extends User {
    /**
     * Constructs a Client with the specified attributes.
     *
     * @param user     the username of the client
     * @param password the password of the client
     * @param name     the name of the client
     * @param surname  the surname of the client
     * @param email    the email address of the client
     * @param bornDate the date of birth of the client
     * @param image    the profile image of the client
     */
    public Client(String user, String password, String name, String surname, String email, LocalDate bornDate, byte[] image) {
        super(user, password, name, surname, email, bornDate, image);
    }

    /**
     * Default constructor for Client.
     */
    public Client() {
    }

    /**
     * Returns a string representation of the client.
     *
     * @return a string representation of the client
     */
    @Override
    public String toString() {
        return "Client{" + super.toString();
    }
}
