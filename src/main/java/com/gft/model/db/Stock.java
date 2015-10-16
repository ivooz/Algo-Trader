package com.gft.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */

@Entity
public class Stock {

    @Id
    @Column(name="Ticker", unique=true)
    private String ticker;
    private String fullName;
    @OneToMany(targetEntity=Algorithm.class)
    private List<Algorithm> algorithms;
    private String type;

    public Stock() {
    	algorithms = new ArrayList<>();
	}

	public List<Algorithm> getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithms(List<Algorithm> algorithms) {
        this.algorithms = algorithms;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
		return "Stock [ticker=" + ticker + ", fullName=" + fullName + ", algorithms=" + algorithms + "]";
	}
    
}
