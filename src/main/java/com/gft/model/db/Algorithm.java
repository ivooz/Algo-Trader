package com.gft.model.db;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Algorithm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(targetEntity = Stock.class)
	private Stock stock;

	@OneToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	@JsonIgnore
	private List<AlgorithmHistory> algorithmHistories;

	private String name;

	private double aggregateGain;

	private double absoluteGain;

	private BigDecimal priceBought;

	public Algorithm() {
		this.absoluteGain = 0;
		this.aggregateGain = 1.0;
		this.priceBought = BigDecimal.ZERO;
	}

	public Algorithm(Stock stock, String name) {
		this();
		this.stock = stock;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public List<AlgorithmHistory> getAlgorithmHistories() {
		return algorithmHistories;
	}

	public void setAlgorithmHistories(List<AlgorithmHistory> algorithmHistories) {
		this.algorithmHistories = algorithmHistories;
	}

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
