package com.gft.model.db;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Algorithm {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @ManyToOne(targetEntity=Stock.class)
    private  Stock stock;
    private  String name;
    private double aggregateGain;
    private double absoluteGain;
    private BigDecimal priceBought;

    public Algorithm(Stock stock, String name) {
        this.stock = stock;
        this.name = name;
    }

    public Algorithm() {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }
}
