package com.gft.component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.gft.model.db.StockHistory;

public class SimpleAverage {
	public SimpleAverage(){}
	
	public BigDecimal compute(ArrayList<StockHistory> listOfStockHistory){
		BigDecimal sum = new BigDecimal(0);
		BigDecimal average = new BigDecimal(0);
		
		for(StockHistory stockHistory: listOfStockHistory){
			sum = sum.add(stockHistory.getClosingPrice());
		}
		
		average =  BigDecimal.valueOf(sum.doubleValue()/listOfStockHistory.size());
		average.setScale(2, RoundingMode.HALF_EVEN);
		return average;
	}

}
