package org.example.model.entity;

import java.util.Objects;

/**
 * Represents a model.
 */
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

    /**
     * Constructs a Model with the specified attributes.
     *
     * @param name        the name of the model
     * @param price       the price of the model
     * @param description the description of the model
     * @param rating      the rating of the model
     * @param image       the image representing the model
     * @param model       the model type
     * @param category    the category of the model
     * @param modeler     the modeler associated with the model
     */
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

    /**
     * Default constructor for Model.
     */
    public Model() {

    }

    /**
     * Gets the ID of the model.
     *
     * @return the ID of the model
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the model.
     *
     * @param id the ID of the model
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the model.
     *
     * @return the name of the model
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the model.
     *
     * @param name the name of the model
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the price of the model.
     *
     * @return the price of the model
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the model.
     *
     * @param price the price of the model
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the description of the model.
     *
     * @return the description of the model
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the model.
     *
     * @param description the description of the model
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the rating of the model.
     *
     * @return the rating of the model
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets the rating of the model.
     *
     * @param rating the rating of the model
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the image representing the model.
     *
     * @return the image representing the model
     */
    public byte[] getImage() {
        return image;
    }

    /**
     * Sets the image representing the model.
     *
     * @param image the image representing the model
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * Gets the type of the model.
     *
     * @return the type of the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the type of the model.
     *
     * @param model the type of the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the category of the model.
     *
     * @return the category of the model
     */
    public ModelCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the model.
     *
     * @param category the category of the model
     */
    public void setCategory(ModelCategory category) {
        this.category = category;
    }

    /**
     * Gets the modeler associated with the model.
     *
     * @return the modeler associated with the model
     */
    public Modeler getModeler() {
        return modeler;
    }

    /**
     * Sets the modeler associated with the model.
     *
     * @param modeler the modeler associated with the model
     */
    public void setModeler(Modeler modeler) {
        this.modeler = modeler;
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
        Model model = (Model) object;
        return id == model.id;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of the model.
     *
     * @return a string representation of the model
     */
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
