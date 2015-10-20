package com.gft.component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gft.model.Action;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;
import com.gft.repository.InsufficientDataException;
import com.gft.service.DataAccessException;

@Component
public class DoubleSimpleMovingAverage implements PredictionAlgorithm {
	
	private int shortInterval;
	private int longInterval;
	private String name;

	public DoubleSimpleMovingAverage(int shortInterval, int longInterval) {
		this.longInterval = longInterval;
		this.shortInterval = shortInterval;
		name = "DobleSimpleMovingAverage" + shortInterval + "and" + longInterval;
	}
	
	public DoubleSimpleMovingAverage() {
	}
	
	public Action predict(Date date, Stock stock, HistoryDAO historyDAO) {

		SimpleAverage simpleAverage = new SimpleAverage();
		List<StockHistory> shortSockList = null;
		List<StockHistory> longStockList = null;
		BigDecimal shortAverage = null;
		BigDecimal longAverage = null;

		try {
			shortSockList = historyDAO.obtainStockHistoryForPeriod(stock, shortInterval);
			longStockList = historyDAO.obtainStockHistoryForPeriod(stock, longInterval);
		} catch (InsufficientDataException | DataAccessException e) {
			return Action.HOLD;
		}

		shortAverage = simpleAverage.compute(shortSockList);
		longAverage = simpleAverage.compute(longStockList);

		if (shortAverage.compareTo(longAverage) == -1) {
			return Action.SELL;
		} else if (shortAverage.compareTo(longAverage) == 1) {
			return Action.BUY;
		} else {
			return Action.HOLD;
		}
	}

	public int getShortInterval() {
		return shortInterval;
	}

	public void setShortInterval(int shortInterval) {
		this.shortInterval = shortInterval;
	}

	public int getLongInterval() {
		return longInterval;
	}

	public void setLongInterval(int longInterval) {
		this.longInterval = longInterval;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
