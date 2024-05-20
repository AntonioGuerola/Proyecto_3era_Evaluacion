package org.example.model.singleton;

import org.example.model.entity.Client;

public class clientSingleton {
    private static clientSingleton _instance;
    private Client currentClient;

    private clientSingleton(Client user) {
        currentClient = user;
    }

    public static clientSingleton getInstance() {
        return _instance;
    }

    public static void getInstance(Client clientToUse) {
        if (clientToUse != null) {
            _instance = new clientSingleton(clientToUse);
        }
    }

    public static void closeSession(){
        _instance=null;
    }

    public Client getCurrentClient() {
        return currentClient;
    }
}
