package org.example.model.singleton;

import org.example.model.entity.Basket;

public class searchSingleton {
    private static searchSingleton _instance;
    private String currentString;

    private searchSingleton(String string) {
        currentString = string;
    }

    public static searchSingleton getInstance() {
        return _instance;
    }

    public static void getInstance(String stringToUse) {
        if (stringToUse != null) {
            _instance = new searchSingleton(stringToUse);
        }
    }

    public static void closeSession(){
        _instance=null;
    }

    public String getCurrentString() {
        return currentString;
    }
}
