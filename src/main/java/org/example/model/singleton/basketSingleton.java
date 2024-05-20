package org.example.model.singleton;

import org.example.model.entity.Basket;

/**
 * Singleton class to manage the current basket in the system.
 */
public class basketSingleton {
    private static basketSingleton _instance;
    private Basket currentBasket;

    /**
     * Initializes a new instance of the basketSingleton class with the provided basket.
     *
     * @param basket The basket to be set as the current basket.
     */
    private basketSingleton(Basket basket) {
        currentBasket = basket;
    }

    /**
     * Retrieves the instance of the BasketSingleton class.
     * @return The instance of BasketSingleton.
     */
    public static basketSingleton getInstance() {
        return _instance;
    }

    /**
     * Initializes the BasketSingleton instance with the provided basket.
     * @param basketToUse The basket to be set as the current basket.
     */
    public static void getInstance(Basket basketToUse) {
        if (basketToUse != null) {
            _instance = new basketSingleton(basketToUse);
        }
    }

    /**
     * Closes the session by resetting the instance to null.
     */
    public static void closeSession() {
        _instance = null;
    }

    /**
     * Retrieves the current basket.
     * @return The current basket.
     */
    public Basket getCurrentBasket() {
        return currentBasket;
    }
}
