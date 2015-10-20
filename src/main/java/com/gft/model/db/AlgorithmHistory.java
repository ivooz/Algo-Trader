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



	public AlgorithmHistory(Algorithm algorithm, Date date,
							double aggregateGain, double absoluteGain) {
		this.algorithm = algorithm;
		this.date = date;
		this.aggregateGain = aggregateGain;
		this.absoluteGain = absoluteGain;
	}
}
