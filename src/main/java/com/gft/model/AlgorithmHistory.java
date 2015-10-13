package com.gft.model;

import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
public class AlgorithmHistory {

    private final Date date;
    private final double aggregateGain;
    private final double absoluteGain;

    public AlgorithmHistory(Date date, double aggregateGain, double absoluteGain) {
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