package com.gft.model.db;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public class Stock {

    private String ticker;
    private String fullName;
    private List<Algorithm> algorithms;
    //TODO OneToMany Relation with algorithm

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

	@Override
	public String toString() {
		return "Stock [ticker=" + ticker + ", fullName=" + fullName + ", algorithms=" + algorithms + "]";
	}
    
}
