package org.example.model.singleton;

import org.example.model.entity.Modeler;

public class modelerSingleton {
    private static modelerSingleton _instance;
    private Modeler currentModeler;

    private modelerSingleton(Modeler user) {
        currentModeler = user;
    }

    public static modelerSingleton getInstance() {
        return _instance;
    }

    public static void getInstance(Modeler modelerToUse) {
        if (modelerToUse != null) {
            _instance = new modelerSingleton(modelerToUse);
        }
    }

    public static void closeSession(){
        _instance=null;
    }

    public Modeler getCurrentModeler() {
        return currentModeler;
    }
}
