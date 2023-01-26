package com.example.akafist.models;

public class ServicesModel {
    private int id;
    private String name;
    private int type;
    private String date;

    public ServicesModel(int id, String name, int type, String date) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
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

    public String getDate() {
        return date;
    }
}
