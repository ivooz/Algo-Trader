package com.gft.model;

/**
 * Created by iozi on 13/10/2015.
 */
public class Stock {

    private String ticker;
    private String fullName;
    //TODO more information about stock
    //TODO OneToMany Relation with algorithm

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
