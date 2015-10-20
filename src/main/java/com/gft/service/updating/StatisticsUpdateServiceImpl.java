package com.gft.service.updating;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.gft.aspect.LogNoArgs;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.component.ListAlgorithmWrapper;
import com.gft.model.Action;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.InsufficientDataException;
import com.gft.service.updating.StatisticsUpdateService;

@Service
public class StatisticsUpdateServiceImpl implements StatisticsUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsUpdateService.class);
	private static final Logger transactionsLogger = LoggerFactory.getLogger("transactions");
	@Autowired
	ListAlgorithmWrapper listawrapper;

	@Autowired
	StockRepository stockRepository;

	public static final String Access_exception = "DATA COULD NOT BE ACCESSED";

	@Override
	public void updateStatistics(Stock stock, Date date, HistoryDAO historyDAO) {
		BigDecimal price = null;
		if (stock.getAlgorithms().size() == 0) {
			assignAlgorithmstoNewAddedStock(stock);
		}
		Iterator<Algorithm> it = stock.getAlgorithms().iterator();
		while (it.hasNext()) {
			Algorithm algorithm = it.next();
			Action action = listawrapper.getAlgorithms().get(algorithm.getName()).predict(date, stock, historyDAO);
			switch (action) {
			case BUY:
				actionBuy(stock, historyDAO, algorithm);
				break;
			case SELL:
				actionSell(stock, historyDAO, algorithm);
				break;
			}
		}
		stockRepository.save(stock);
	}

	private void assignAlgorithmstoNewAddedStock(Stock stock) {
		List<Algorithm> algorithms = new ArrayList<>();
		listawrapper.getAlgorithms().values().forEach(algo -> algorithms.add(new Algorithm(stock, algo.getName())));
		stock.setAlgorithms(algorithms);
	}

	private void actionBuy(Stock stock, HistoryDAO historyDAO, Algorithm algorithm) {
		BigDecimal price = null;
		if (algorithm.getPriceBought().equals(BigDecimal.ZERO)) {
			try {
				price = historyDAO.getCurrentDay(stock).getClosingPrice();
			} catch (InsufficientDataException | DataAccessException e) {
				logger.error(Access_exception);
			}
			try {
				transactionsLogger.info(historyDAO.getCurrentDay(stock).getDate() + " Transaciton for "
						+ algorithm.getName() + " B:" + price);
			} catch (InsufficientDataException | DataAccessException e) {
			}
			algorithm.setPriceBought(price);
		}
	}

	private void actionSell(Stock stock, HistoryDAO historyDAO, Algorithm algorithm) {
		BigDecimal price = null;

		if (!(algorithm.getPriceBought().equals(BigDecimal.ZERO))) {
			try {
				price = historyDAO.getCurrentDay(stock).getClosingPrice();
			} catch (InsufficientDataException | DataAccessException e) {
				logger.error(Access_exception);

			}
			double gain = calculateGain(price.doubleValue(), algorithm.getPriceBought().doubleValue());
			// TODO FIXIT
			try {
				transactionsLogger.info(historyDAO.getCurrentDay(stock).getDate() + " Transaciton for "
						+ algorithm.getName() + " B:" + algorithm.getPriceBought().doubleValue() + " S:"
						+ price.doubleValue() + " G: " + gain);
			} catch (InsufficientDataException | DataAccessException e) {
			}
			algorithm.setPriceBought(BigDecimal.ZERO);
			algorithm.setAbsoluteGain(algorithm.getAbsoluteGain() + gain);
			algorithm.setAggregateGain(algorithm.getAggregateGain() * (1 + gain));
		}
	}

	private double calculateGain(double price, double price_bought) {
		double gain = price - price_bought;
		gain = gain / price_bought;
		return gain;
	}
}
