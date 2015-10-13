package com.gft.model;

import java.math.BigDecimal;

/**
 * Created by iozi on 13/10/2015.
 */
public class Algorithm {

    private String name;
    private double aggregateGain;
    private double absoluteGain;
    private BigDecimal priceBought;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
