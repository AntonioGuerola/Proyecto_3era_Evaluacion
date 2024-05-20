package org.example.model.singleton;

import org.example.model.entity.Model;
import org.example.model.entity.Modeler;

public class modelSingleton {
    private static modelSingleton _instance;
    private Model currentModel;

    private modelSingleton(Model model) {
        currentModel = model;
    }

    public static modelSingleton getInstance() {
        return _instance;
    }

    public static void getInstance(Model modelToUse) {
        if (modelToUse != null) {
            _instance = new modelSingleton(modelToUse);
        }
    }

    public static void closeSession(){
        _instance=null;
    }

    public Model getCurrentModel() {
        return currentModel;
    }
}
