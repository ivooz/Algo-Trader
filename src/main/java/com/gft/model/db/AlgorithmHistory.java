package com.gft.model.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Created by iozi on 13/10/2015.
 */

@Entity
public class AlgorithmHistory {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}
    @OneToOne(targetEntity=Algorithm.class)
	private final Algorithm algorithm;
    public Algorithm getAlgorithm() {
		return algorithm;
	}

	private final Date date;
    private final double aggregateGain;
    private final double absoluteGain;

    public AlgorithmHistory(Algorithm algorithm, Date date, double aggregateGain, double absoluteGain) {
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
