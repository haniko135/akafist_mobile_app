package com.example.akafist.models;

public class PrayersModels {
    private String namePrayer;
    private Integer textPrayer;

    public PrayersModels(String namePrayer, Integer textPrayer) {
        this.namePrayer = namePrayer;
        this.textPrayer = textPrayer;
    }

    public String getNamePrayer() {
        return namePrayer;
    }

    public Integer getTextPrayer() {
        return textPrayer;
    }
}
