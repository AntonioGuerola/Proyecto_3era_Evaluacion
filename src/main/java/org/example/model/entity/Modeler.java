package org.example.model.entity;

import java.time.LocalDate;

/**
 * Represents a modeler entity that.
 */
public class Modeler extends User {
    /**
     * Constructs a Modeler with the specified attributes.
     *
     * @param user     the username of the modeler
     * @param password the password of the modeler
     * @param name     the name of the modeler
     * @param surname  the surname of the modeler
     * @param email    the email address of the modeler
     * @param bornDate the date of birth of the modeler
     * @param image    the profile image of the modeler
     */
    public Modeler(String user, String password, String name, String surname, String email, LocalDate bornDate, byte[] image) {
        super(user, password, name, surname, email, bornDate, image);
    }

    /**
     * Default constructor for Modeler.
     */
    public Modeler() {
    }

    /**
     * Returns a string representation of the modeler.
     *
     * @return a string representation of the modeler
     */
    @Override
    public String toString() {
        return "Modeler{" + super.toString();
    }
}
