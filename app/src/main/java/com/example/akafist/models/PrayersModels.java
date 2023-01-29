package com.example.akafist.models;

public class PrayersModels {
    private final String namePrayer;
    private final String textPrayer;
    private final int prev;
    private final int next;

    public PrayersModels(String namePrayer, String textPrayer, int prev, int next) {
        this.namePrayer = namePrayer;
        this.textPrayer = textPrayer;
        this.prev = prev;
        this.next = next;
    }

    public String getNamePrayer() {
        return namePrayer;
    }

    public String getTextPrayer() {
        return textPrayer;
    }

    public int getPrev() {
        return prev;
    }

    public int getNext() {
        return next;
    }
}
