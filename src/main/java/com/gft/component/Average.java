package com.gft.component;

import java.math.BigDecimal;
import java.util.List;

import com.gft.model.db.StockHistory;

public interface Average {

	public BigDecimal compute(List<StockHistory> listOfStockHistory);

	default public int findSecoundCapitalLetter(String str) {
		for (int i = 1; i < str.length(); i++) {
			if (Character.isUpperCase(str.charAt(i)) == true) {
				return i;
			}
		}
		return -1;
	}

}
