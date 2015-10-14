package com.gft.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.gft.model.Action;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;

@Component
public class SimpleMovingAverage implements PredictionAlgorithm {

	int interval;
	private String name;

	public SimpleMovingAverage(int interval) {

		this.interval = interval;
		name = "SimpleMovingAverage" + interval;

	}
	
	public SimpleMovingAverage() {

		this.interval = 15;
		name = "SimpleMovingAverage" + interval;

	}
	

	public Action predict(Date date, Stock stock, HistoryDAO historyDAO){

		Action action;

		SimpleAverage simpleAverage = new SimpleAverage();
		BigDecimal average = simpleAverage.compute(historyDAO.obtainStockHistoryForPeriod(stock, interval));
		ArrayList<StockHistory> listOfStock = historyDAO.obtainStockHistoryForPeriod(stock, interval);
		if (average.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == -1) { // average is smaller than price
			return Action.BUY;
		} else if (average.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == 1) {
			return Action.SELL;
		} else {
			return Action.HOLD;
		}
	}

	public String getName() {
		return name;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public void setName(String name) {
		this.name = name;
	}

}
