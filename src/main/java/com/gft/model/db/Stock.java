package com.gft.model.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
    //TODO OneToMany Relation with algorithm

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

}