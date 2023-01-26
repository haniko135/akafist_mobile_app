package com.example.akafist.models;

public class ServicesModel {
    private int id;
    private String name;
    private int type;

    public ServicesModel(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
