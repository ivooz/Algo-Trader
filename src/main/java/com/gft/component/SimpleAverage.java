package com.gft.component;

import com.gft.model.db.StockHistory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SimpleAverage {
	public BigDecimal compute(List<StockHistory> listOfStockHistory){
	
		BigDecimal average = new BigDecimal(0);
		for(StockHistory stockHistory: listOfStockHistory){
			average = average.add(stockHistory.getClosingPrice());
		}
		average =  average.divide(new BigDecimal(listOfStockHistory.size()),2 ,RoundingMode.HALF_EVEN);
		return average;
	}
}
