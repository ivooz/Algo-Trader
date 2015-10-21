package com.gft.component;

import com.gft.model.Action;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.InsufficientDataException;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class MovingAverage implements PredictionAlgorithm {

	private static final Logger logger = LoggerFactory.getLogger(MovingAverage.class);

	private int interval;
	private String name;
	private Average average;
	
	public MovingAverage(int interval, Average average) {
		String averageName = average.getClass().getSimpleName();
		String firstPartAverageName = averageName.substring(0, average.findSecoundCapitalLetter(averageName));
		String secondPartAverageName = averageName.substring(average.findSecoundCapitalLetter(averageName), averageName.length());
		
		this.interval = interval;
		name = firstPartAverageName + "Moving" + secondPartAverageName + interval; 
		this.average = average;
	}
	
	public MovingAverage() {
	}
	
	public Action predict(Date date, Stock stock, HistoryDAO historyDAO){
 
		//SimpleAverage simpleAverage = new SimpleAverage();
		BigDecimal computedAverage = null;
		List<StockHistory> listOfStock = null;
		try {
			computedAverage = average.compute(historyDAO.obtainStockHistoryForPeriod(stock, interval));
			listOfStock = historyDAO.obtainStockHistoryForPeriod(stock, interval);
			if (computedAverage.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == -1) { // average is smaller than price
				return Action.BUY;
			} else if (computedAverage.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == 1) {
				return Action.SELL;
			}
		} catch (InsufficientDataException | DataAccessException e) {
			//TODO FIXME
		}
		return Action.HOLD;

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
