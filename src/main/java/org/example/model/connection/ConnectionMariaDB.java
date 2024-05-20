package org.example.model.connection;

import org.example.model.utils.XMLManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectionMariaDB is a singleton class responsible for managing a connection
 * to a MariaDB database. It reads the connection properties from an XML file
 * and establishes the connection using these properties.
 */
public class ConnectionMariaDB {
    private final static String FILE = "connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    /**
     * Private constructor to prevent instantiation from outside the class.
     * It reads the connection properties from the specified XML file and
     * attempts to establish a connection to the database.
     */
    private ConnectionMariaDB() {
        ConnectionProperties properties = (ConnectionProperties) XMLManager.readXML(new ConnectionProperties(), FILE);
        try {
            conn = DriverManager.getConnection(properties.getURL(), properties.getUser(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            conn = null;
        }
    }

    /**
     * Returns the singleton instance of the ConnectionMariaDB class. If the
     * instance does not exist, it creates one and establishes the database
     * connection.
     *
     * @return the connection to the MariaDB database
     */
    public static Connection getConnection() {
        if (_instance == null) {
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }

    /**
     * Closes the database connection if it is not null. This method should be
     * called to release the database resources when they are no longer needed.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

