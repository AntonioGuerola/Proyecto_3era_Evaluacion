package org.example.model.entity;

import java.util.Locale;
import java.util.Objects;

public class Model {
    private int id;
    private String name;
    private double price;
    private String description;
    private double rating;
    private byte[] image;
    private String model;
    private ModelCategory category;
    private Modeler modeler;

    public Model(String name, double price, String description, double rating, byte[] image, String model, ModelCategory category, Modeler modeler) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.rating = rating;
        this.image = image;
        this.model = model;
        this.category = category;
        this.modeler = modeler;
    }

    public Model (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ModelCategory getCategory() {
        return category;
    }

    public void setCategory(ModelCategory category) {
        this.category = category;
    }

    public Modeler getModeler() {
        return modeler;
    }

    public void setModeler(Modeler modeler) {
        this.modeler = modeler;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Model model = (Model) object;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", model='" + model + '\'' +
                ", category=" + category +
                ", modeler=" + modeler +
                '}';
    }
}
