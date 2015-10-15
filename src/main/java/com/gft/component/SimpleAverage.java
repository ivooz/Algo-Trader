package com.gft.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.gft.model.db.StockHistory;

public class SimpleAverage {
	public SimpleAverage(){}
	
	public BigDecimal compute(List<StockHistory> listOfStockHistory){
	
		BigDecimal average = new BigDecimal(0);
		for(StockHistory stockHistory: listOfStockHistory){
			average = average.add(stockHistory.getClosingPrice());
		}
		average =  average.divide(new BigDecimal(listOfStockHistory.size()),2 ,RoundingMode.HALF_EVEN);
		return average;
	}

}
