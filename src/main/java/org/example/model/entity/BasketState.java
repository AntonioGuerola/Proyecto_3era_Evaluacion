package org.example.model.entity;

public enum BasketState {
    INPROCESS("INPROCESS"),
    BOUGHT("BOUGHT");

    private String name;

    BasketState(String name) {
        this.name = name;
    }

    public String getName(String basketState) {
        return name;
    }
}
