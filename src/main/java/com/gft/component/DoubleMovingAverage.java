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
public class DoubleMovingAverage implements PredictionAlgorithm {

	private int shortInterval;
	private int longInterval;
	private String name;
	private Average average;

	public DoubleMovingAverage(int shortInterval, int longInterval, Average average) {
		String averageName = average.getClass().getSimpleName();
		String firstPartAverageName = averageName.substring(0, average.findSecoundCapitalLetter(averageName));
		String secondPartAverageName = averageName.substring(average.findSecoundCapitalLetter(averageName),
				averageName.length());

		this.longInterval = longInterval;
		this.shortInterval = shortInterval;
		name = "Double" + firstPartAverageName + "Moving" + secondPartAverageName + shortInterval + "and"
				+ longInterval;
		this.average = average;
	}

	public DoubleMovingAverage() {
	}

	public Action predict(Date date, Stock stock, HistoryDAO historyDAO) {

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

		shortAverage = average.compute(shortSockList);
		longAverage = average.compute(longStockList);

		if (shortAverage.compareTo(longAverage) == -1) {
			return Action.SELL;
		} else if (shortAverage.compareTo(longAverage) == 1) {
			return Action.BUY;
		} else {
			return Action.HOLD;
		}
	}

	public Average getAverage() {
		return average;
	}

	public void setAverage(Average average) {
		this.average = average;
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
