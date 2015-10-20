package com.gft.component;

import java.math.BigDecimal;
import java.util.List;

import com.gft.model.db.StockHistory;

public class ExponentialAverage {
	
	public BigDecimal compute(List<StockHistory> listOfStockHistory){
		
		int listSize = listOfStockHistory.size();
		double alfa = 2/(listSize);
		
		double numerator = listOfStockHistory.get(listSize-1).getClosingPrice().doubleValue(); // licznik
		double denominator = 1; //mianownik
		
		for(int i = 1; i < listOfStockHistory.size(); i++){
			numerator += Math.pow((1-alfa), i) * listOfStockHistory.get(listSize-i-1).getClosingPrice().doubleValue(); 
			denominator += Math.pow((1-alfa) , i);
		}
		return new BigDecimal(numerator/denominator);		
	}

}
