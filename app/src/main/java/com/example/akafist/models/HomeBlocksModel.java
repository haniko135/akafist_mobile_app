package com.example.akafist.models;

public class HomeBlocksModel {
    private String date;
    private String dateTxt;
    private String name;
    private int links;

    public HomeBlocksModel(String date, String dateTxt, String name) {
        this.date = date;
        this.dateTxt = dateTxt;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getDateTxt() {
        return dateTxt;
    }

    public String getName() {
        return name;
    }

    public void setLinks(int links) {
        this.links = links;
    }

    public int getLinks() {
        return links;
    }
}
