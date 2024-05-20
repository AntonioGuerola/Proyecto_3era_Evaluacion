package org.example.model.singleton;

import org.example.model.entity.Client;

/**
 * Singleton class to manage the current client in the system.
 */
public class clientSingleton {
    private static clientSingleton _instance;
    private Client currentClient;

    /**
     * Initializes a new instance of the clientSingleton class with the provided client.
     *
     * @param user The user to be set as the current client.
     */
    private clientSingleton(Client user) {
        currentClient = user;
    }

    /**
     * Retrieves the instance of the ClientSingleton class.
     * @return The instance of ClientSingleton.
     */
    public static clientSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the ClientSingleton instance with the provided client.
     * @param clientToUse The client to be set as the current client.
     */
    public static void getInstance(Client clientToUse) {
        if (clientToUse != null) {
            _instance = new clientSingleton(clientToUse);
        }
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current client.
     * @return The current client.
     */
    public Client getCurrentClient() {
        return currentClient;
    }
}
