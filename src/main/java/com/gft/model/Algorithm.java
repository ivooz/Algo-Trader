package com.gft.model;

import java.math.BigDecimal;

/**
 * Created by iozi on 13/10/2015.
 */
public class Algorithm {

    private final Stock stock;
    private final String name;
    private double aggregateGain;
    private double absoluteGain;
    private BigDecimal priceBought;

    public Algorithm(Stock stock, String name) {
        this.stock = stock;
        this.name = name;
    }

    public Stock getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public double getAggregateGain() {
        return aggregateGain;
    }

    public void setAggregateGain(double aggregateGain) {
        this.aggregateGain = aggregateGain;
    }

    public double getAbsoluteGain() {
        return absoluteGain;
    }

    public void setAbsoluteGain(double absoluteGain) {
        this.absoluteGain = absoluteGain;
    }

    public BigDecimal getPriceBought() {
        return priceBought;
    }

    public void setPriceBought(BigDecimal priceBought) {
        this.priceBought = priceBought;
    }
}
