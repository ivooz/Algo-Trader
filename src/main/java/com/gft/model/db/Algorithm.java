package com.gft.model.db;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.jpamodelgen.xml.jaxb.CascadeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Algorithm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne(targetEntity = Stock.class)
	private Stock stock;
	@OneToMany(fetch = FetchType.LAZY, cascade = javax.persistence.CascadeType.ALL)
	private List<AlgorithmHistory> algorithmHistories;
	private String name;
	private double aggregateGain;
	private double absoluteGain;
	private BigDecimal priceBought;

	public Algorithm(Stock stock, String name) {
		this();
		this.stock = stock;
		this.name = name;

	}

	public Algorithm() {
		this.absoluteGain = 0;
		this.aggregateGain = 1.0;
		this.priceBought = BigDecimal.ZERO;
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

	public List<AlgorithmHistory> getAlgorithmHistories() {
		return algorithmHistories;
	}

	public void setAlgorithmHistories(List<AlgorithmHistory> algorithmHistories) {
		this.algorithmHistories = algorithmHistories;
	}
}
