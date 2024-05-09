package org.example.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Basket {
    private int id;
    private double finalPrice;
    private HashMap<Model,Integer> models;

    public Basket(int id, double finalPrice, HashMap<Model,Integer> models) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.models = models;
    }

    public Basket(){
        id=-1;
        finalPrice=0;
        this.models=new HashMap<>();
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

    public HashMap<Model,Integer>getModels() {
        return models;
    }

    public void setModels(HashMap<Model,Integer> models) {
        this.models = models;
    }

    public void addModel(Model model) {
        if(models.containsKey(model)){
            int currentQuantity = models.get(model);
            models.put(model,currentQuantity+1);
        }else{
            models.put(model,1);
        }
    }
    public void removeModel(Model model){
        if(models.containsKey(model)){
            int currentQuantity = models.get(model);
            if(currentQuantity==1){
                models.remove(model);
            }else{
                models.put(model,currentQuantity-1);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Basket basket = (Basket) object;
        return id == basket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "id=" + id +
                ", finalPrice=" + finalPrice +
                ", model=" + models +
                '}';
    }
}
