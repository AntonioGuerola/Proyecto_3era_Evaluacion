package org.example.model.singleton;

import org.example.model.entity.Model;

/**
 * Singleton class to manage the current model in the system.
 */
public class modelSingleton {
    private static modelSingleton _instance;
    private Model currentModel;

    /**
     * Initializes a new instance of the modelSingleton class with the provided model.
     *
     * @param model The model to be set as the current model.
     */
    private modelSingleton(Model model) {
        currentModel = model;
    }

    /**
     * Retrieves the instance of the ModelSingleton class.
     * @return The instance of ModelSingleton.
     */
    public static modelSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the ModelSingleton instance with the provided model.
     * @param modelToUse The model to be set as the current model.
     */
    public static void getInstance(Model modelToUse) {
        if (modelToUse != null) {
            _instance = new modelSingleton(modelToUse);
        }
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current model.
     * @return The current model.
     */
    public Model getCurrentModel() {
        return currentModel;
    }
}
