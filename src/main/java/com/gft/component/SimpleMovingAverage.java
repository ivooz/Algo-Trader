package com.gft.component;

import com.gft.model.Action;
import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;
import com.gft.repository.InsufficientDataException;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class SimpleMovingAverage implements PredictionAlgorithm {

	private static final Logger logger = LoggerFactory.getLogger(SimpleMovingAverage.class);

	private int interval;
	private String name;

	public SimpleMovingAverage(int interval) {
		this.interval = interval;
		name = "SimpleMovingAverage" + interval;
	}
	
	public SimpleMovingAverage() {
	}
	
	public Action predict(Date date, Stock stock, HistoryDAO historyDAO){
 
		SimpleAverage simpleAverage = new SimpleAverage();
		BigDecimal average = null;
		List<StockHistory> listOfStock = null;
		try {
			average = simpleAverage.compute(historyDAO.obtainStockHistoryForPeriod(stock, interval));
			listOfStock = historyDAO.obtainStockHistoryForPeriod(stock, interval);
			if (average.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == -1) { // average is smaller than price
				return Action.BUY;
			} else if (average.compareTo(listOfStock.get(listOfStock.size() - 1).getClosingPrice()) == 1) {
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
