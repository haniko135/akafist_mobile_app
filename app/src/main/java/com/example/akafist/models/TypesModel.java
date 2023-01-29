package com.example.akafist.models;

public class TypesModel {
    private final int id;
    private final String name;

    public TypesModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
