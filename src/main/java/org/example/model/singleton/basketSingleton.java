package org.example.model.singleton;

import org.example.model.entity.Basket;
import org.example.model.entity.Client;

public class basketSingleton {
    private static basketSingleton _instance;
    private Basket currentBasket;

    private basketSingleton(Basket basket) {
        currentBasket = basket;
    }

    public static basketSingleton getInstance() {
        return _instance;
    }

    public static void getInstance(Basket basketToUse) {
        if (basketToUse != null) {
            _instance = new basketSingleton(basketToUse);
        }
    }

    public static void closeSession(){
        _instance=null;
    }

    public Basket getCurrentBasket() {
        return currentBasket;
    }
}
