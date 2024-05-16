package org.example.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Basket {
    private int id;
    private double finalPrice;
    private HashMap<Integer,Model> models;
    private Client client;
    private BasketState basketState;

    public Basket(int id, double finalPrice, HashMap<Integer, Model> models, Client client, BasketState basketState) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.models = models;
        this.client = client;
        this.basketState = basketState;
    }

    public Basket(){
        id=-1;
        finalPrice=0;
        this.models=new HashMap<>();
        this.basketState = BasketState.INPROCESS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public HashMap<Integer,Model>getModels() {
        return models;
    }

    public void setModels(HashMap<Integer,Model> models) {
        this.models = models;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BasketState getBasketState() {
        return basketState;
    }

    public void setBasketState(BasketState basketState) {
        this.basketState = basketState;
    }

    public boolean addModel(Model model) {
        boolean result = false;
        if (model != null && model.getId() > 0){
            if(models == null){
                models = new HashMap<>();
            }
            if (models.containsKey(model.getId())){
                models.put(model.getId(), model);
                result = true;
            }
        }
        return result;
    }
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Basket basket = (Basket) object;
        return id == basket.id && basketState == basket.basketState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, basketState);
    }

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
