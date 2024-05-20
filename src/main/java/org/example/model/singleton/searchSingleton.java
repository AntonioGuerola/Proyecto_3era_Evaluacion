package org.example.model.singleton;

/**
 * Singleton class to manage the current search string in the system.
 */
public class searchSingleton {
    private static searchSingleton _instance;
    private String currentString;

    /**
     * Initializes a new instance of the searchSingleton class with the provided search string.
     *
     * @param string The string to be set as the current search string.
     */
    private searchSingleton(String string) {
        currentString = string;
    }

    /**
     * Retrieves the instance of the SearchSingleton class.
     * @return The instance of SearchSingleton.
     */
    public static searchSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the SearchSingleton instance with the provided search string.
     * @param stringToUse The string to be set as the current search string.
     */
    public static void getInstance(String stringToUse) {
        if (stringToUse != null) {
            _instance = new searchSingleton(stringToUse);
        }
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current search string.
     * @return The current search string.
     */
    public String getCurrentString() {
        return currentString;
    }
}
