package com.example.akafist.models;

public class LinksModel {
    private int id;
    private String url;
    private String name;

    public LinksModel(int id, String url, String name) {
        this.id = id;
        this.url = url;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
