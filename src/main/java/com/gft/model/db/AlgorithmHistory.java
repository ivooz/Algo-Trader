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
	private final Date date;
	@OneToOne(targetEntity = Algorithm.class)
	private final Algorithm algorithm;
	private final double aggregateGain;
	private final double absoluteGain;
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
