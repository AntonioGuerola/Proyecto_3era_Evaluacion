package org.example.model.entity;

/**
 * Enum representing the state of a basket.
 */
public enum BasketState {
    INPROCESS("INPROCESS"),
    BOUGHT("BOUGHT");

    private String name;

    /**
     * Constructs a BasketState enum with the specified name.
     *
     * @param name the name of the basket state
     */
    BasketState(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the basket state.
     *
     * @return the name of the basket state
     */
    public String getName(String basketState) {
        return name;
    }
}
