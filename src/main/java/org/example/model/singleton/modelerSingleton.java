package org.example.model.singleton;

import org.example.model.entity.Modeler;

/**
 * Singleton class to manage the current modeler in the system.
 */
public class modelerSingleton {
    private static modelerSingleton _instance;
    private Modeler currentModeler;

    /**
     * Initializes a new instance of the modelerSingleton class with the provided modeler.
     *
     * @param user The user to be set as the current modeler.
     */
    private modelerSingleton(Modeler user) {
        currentModeler = user;
    }

    /**
     * Retrieves the instance of the ModelerSingleton class.
     * @return The instance of ModelerSingleton.
     */
    public static modelerSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the ModelerSingleton instance with the provided modeler.
     * @param modelerToUse The modeler to be set as the current modeler.
     */
    public static void getInstance(Modeler modelerToUse) {
        if (modelerToUse != null) {
            _instance = new modelerSingleton(modelerToUse);
        }
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current modeler.
     * @return The current modeler.
     */
    public Modeler getCurrentModeler() {
        return currentModeler;
    }
}
