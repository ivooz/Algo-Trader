package com.gft.service;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.InsufficientDataException;

import java.math.BigDecimal;
import java.util.*;

public class HistoryDAOmock implements HistoryDAO {

	ArrayList<StockHistory> history = new ArrayList<StockHistory>();

	public List<StockHistory> getHistory() {
		return history;
	}

	private StockHistory getStockHistoryWithMockData(Date date, Stock stock, BigDecimal price, long volume) {

		return new StockHistory(date, stock, price, price, price, price, volume);
	}
	
	public void setRisingPrice(int days) {
		Calendar firstDay = GregorianCalendar.getInstance();
		firstDay.add(Calendar.DAY_OF_YEAR, -days);
		
		double startPrice = 1.0;
		long volume = 100l;
		
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		stock.setTicker("KGH");
		
		for (int i = 0; i < days; i++) {
			firstDay.add(firstDay.DATE, 1);
			StockHistory stockHistory = getStockHistoryWithMockData(firstDay.getTime(), stock, new BigDecimal(startPrice++), volume);
			stockHistory.setId(i);
			history.add(stockHistory);
		}
	}

	

	public void setDecrisingPrice(int days) {
		Calendar firstDay = GregorianCalendar.getInstance();
		firstDay.add(Calendar.DAY_OF_YEAR, -days);
		
		double startPrice = 2999.0;
		long volume = 100l;
		
		Stock stock = new Stock();
		stock.setFullName("KGHM");
		stock.setTicker("KGH");
		
		for (int i = 0; i < days; i++) {
			firstDay.add(firstDay.DATE, 1);
			StockHistory stockHistory = getStockHistoryWithMockData(firstDay.getTime(), stock, new BigDecimal(startPrice--), volume);
			stockHistory.setId(i);
			history.add(stockHistory);
		}
	}

	@Override
	public ArrayList<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) {
		ArrayList<StockHistory> stocksInPeriod = new ArrayList<>();
		int startIndex = history.size() - days;
		for (int i = startIndex; i < history.size(); i++) {
			stocksInPeriod.add(history.get(i));
		}
		return stocksInPeriod;
	}

	@Override
	public StockHistory getCurrentDay(Stock stock) throws InsufficientDataException, DataAccessException {
		return null;
	}

	@Override
	public int getHistorySize(Stock stock) throws InsufficientDataException, DataAccessException {
		return 0;
	}
}
