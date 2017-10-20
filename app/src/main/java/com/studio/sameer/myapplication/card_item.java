package com.studio.sameer.myapplication;

/**
 * Created by Sameer on 05/10/2017.
 */

public class card_item {
    private String currencyName;
    private String rate;
    private String date;

    public card_item(String currencyName, String rate, String date) {
        this.currencyName = currencyName;
        this.rate = rate;
        this.date = date;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getRate() {
        return rate;
    }

    public String getDate() {
        return date;
    }
}