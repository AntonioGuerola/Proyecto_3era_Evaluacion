package org.example.model.connection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * ConnectionProperties is a class that holds the properties required to establish
 * a connection to a MariaDB database. It includes fields for server, port, database,
 * user, and password, and it can be serialized to and from XML.
 */
@XmlRootElement(name = "connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String server;
    private String port;
    private String database;
    private String user;
    private String password;

    /**
     * Constructs a ConnectionProperties object with the specified properties.
     *
     * @param server   the database server address
     * @param port     the port on which the database server is running
     * @param database the name of the database to connect to
     * @param user     the username for database authentication
     * @param password the password for database authentication
     */
    public ConnectionProperties(String server, String port, String database, String user, String password) {
        this.server = server;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    /**
     * Default constructor for creating an empty ConnectionProperties object.
     */
    public ConnectionProperties() {
    }

    /**
     * Gets the database server address.
     *
     * @return the server address
     */
    public String getServer() {
        return server;
    }

    /**
     * Sets the database server address.
     *
     * @param server the server address
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Gets the port on which the database server is running.
     *
     * @return the port number
     */
    public String getPort() {
        return port;
    }

    /**
     * Sets the port on which the database server is running.
     *
     * @param port the port number
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * Gets the name of the database to connect to.
     *
     * @return the database name
     */
    public String getDatabase() {
        return database;
    }


    /**
     * Sets the name of the database to connect to.
     *
     * @param database the database name
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * Gets the username for database authentication.
     *
     * @return the username
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the username for database authentication.
     *
     * @param user the username
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets the password for database authentication.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for database authentication.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the ConnectionProperties object.
     *
     * @return a string containing all the connection properties
     */
    @Override
    public String toString() {
        return "ConnectionProperties{" +
                "server='" + server + '\'' +
                ", port='" + port + '\'' +
                ", database='" + database + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Constructs and returns the JDBC URL for connecting to the MariaDB database
     * using the current properties.
     *
     * @return the JDBC URL
     */
    public String getURL() {
        return "jdbc:mariadb://" + server + ":" + port + "/" + database;
    }
}

