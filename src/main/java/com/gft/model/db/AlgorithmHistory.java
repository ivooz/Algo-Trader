package com.gft.model.db;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */

@Entity
public class AlgorithmHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private  Date date;
	public void setDate(Date date) {
		this.date = date;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public void setAggregateGain(double aggregateGain) {
		this.aggregateGain = aggregateGain;
	}

	public void setAbsoluteGain(double absoluteGain) {
		this.absoluteGain = absoluteGain;
	}

	public AlgorithmHistory() {
		super();
	}

	@OneToOne(targetEntity = Algorithm.class)
	private Algorithm algorithm;
	private  double aggregateGain;
	private  double absoluteGain;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public AlgorithmHistory(Algorithm algorithm, Date date,
							double aggregateGain, double absoluteGain) {
		this.algorithm = algorithm;
		this.date = date;
		this.aggregateGain = aggregateGain;
		this.absoluteGain = absoluteGain;
	}

	public Date getDate() {
		return date;
	}

	public double getAggregateGain() {
		return aggregateGain;
	}

	public double getAbsoluteGain() {
		return absoluteGain;
	}


}
