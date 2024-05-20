package org.example.model.entity;

import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a basket entity that holds models selected by a client for purchase.
 */
public class Basket {
    private int id;
    private double finalPrice;
    private HashMap<Integer, Model> models;
    private Client client;
    private BasketState basketState;

    /**
     * Constructs a Basket object with specified parameters.
     *
     * @param id          the ID of the basket
     * @param finalPrice  the final price of the basket
     * @param models      the models in the basket
     * @param client      the client associated with the basket
     * @param basketState the state of the basket
     */
    public Basket(int id, double finalPrice, HashMap<Integer, Model> models, Client client, BasketState basketState) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.models = models;
        this.client = client;
        this.basketState = basketState;
    }

    /**
     * Constructs a Basket object with default values.
     * The ID is set to -1, finalPrice to 0, and basketState to INPROCESS.
     */
    public Basket() {
        id = -1;
        finalPrice = 0;
        this.models = new HashMap<>();
        this.basketState = BasketState.INPROCESS;
    }

    /**
     * Returns the ID of the basket.
     *
     * @return the ID of the basket
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the basket.
     *
     * @param id the ID of the basket
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the final price of the basket.
     *
     * @return the final price of the basket
     */
    public double getFinalPrice() {
        return finalPrice;
    }

    /**
     * Sets the final price of the basket.
     *
     * @param finalPrice the final price of the basket
     */
    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    /**
     * Returns the models in the basket.
     *
     * @return the models in the basket
     */
    public HashMap<Integer, Model> getModels() {
        return models;
    }

    /**
     * Sets the models in the basket.
     *
     * @param models the models in the basket
     */
    public void setModels(HashMap<Integer, Model> models) {
        this.models = models;
    }

    /**
     * Returns the client associated with the basket.
     *
     * @return the client associated with the basket
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client associated with the basket.
     *
     * @param client the client associated with the basket
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Returns the state of the basket.
     *
     * @return the state of the basket
     */
    public BasketState getBasketState() {
        return basketState;
    }

    /**
     * Sets the state of the basket.
     *
     * @param basketState the state of the basket
     */
    public void setBasketState(BasketState basketState) {
        this.basketState = basketState;
    }

    /**
     * Adds a model to the basket.
     *
     * @param model the model to add
     * @return true if the model was added successfully, otherwise false
     */
    public boolean addModel(Model model) {
        boolean result = false;
        if (model != null && model.getId() > 0) {
            if (models == null) {
                models = new HashMap<>();
            }
            if (models.containsKey(model.getId())) {
                models.put(model.getId(), model);
                result = true;
            }
        }
        return result;
    }

    /**
     * Removes a model from the basket.
     *
     * @param model the model to remove
     * @return true if the model was removed successfully, otherwise false
     */
    public boolean removeModel(Model model) {
        boolean result = false;
        if (model != null && model.getId() > 0) {
            if (models != null) {
                if (models.containsKey(model.getId())) {
                    models.remove(model.getId());
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare
     * @return true if this object is the same as the object argument; false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Basket basket = (Basket) object;
        return id == basket.id && basketState == basket.basketState;
    }

    /**
     * Returns a hash code value for the basket.
     *
     * @return a hash code value for the basket
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, basketState);
    }

    /**
     * Returns a string representation of the basket.
     *
     * @return a string representation of the basket
     */
    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", finalPrice=" + finalPrice +
                ", models=" + models +
                ", client=" + client +
                ", basketState=" + basketState +
                '}';
    }
}
