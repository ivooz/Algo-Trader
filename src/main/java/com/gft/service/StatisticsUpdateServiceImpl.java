package com.gft.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
	@Autowired
	ListAlgorithmWrapper listawrapper;

	public static final String Access_exception = "DATA COULD NOT BE ACCESSED";
	@Override
	public void updateStatistics(Stock stock, Date date,
			HistoryDAO historyDAO) {
		BigDecimal price = null;

		if (stock.getAlgorithms().size() == 0) {

			setAlgorithms(stock);
		}
		Iterator<Algorithm> it = stock.getAlgorithms().iterator();
		while (it.hasNext()) {
			Algorithm algorithm = it.next();
	
			Action action = listawrapper.getSimpleMovingAverages()
					.get(algorithm.getName()).predict(date, stock, historyDAO);

		

			if (action == Action.BUY) {

				if (algorithm.getPriceBought().equals(BigDecimal.ZERO)) {

					try {
						logger.info("Obtaining info from HistoryDAO");
						price = historyDAO.obtainStockHistoryForPeriod(stock, 1)
								.get(0).getClosingPrice();
					} catch (InsufficientDataException
							| DataAccessException e) {
						logger.error(Access_exception);					}
					algorithm.setPriceBought(price);
				}

			}
			if (action == Action.SELL) {

				if (!(algorithm.getPriceBought().equals(BigDecimal.ZERO))) {
					try {
						logger.info("Obtaining info from HistoryDAO");
						price = historyDAO.obtainStockHistoryForPeriod(stock, 1)
								.get(0).getClosingPrice();

					} catch (InsufficientDataException
							| DataAccessException e) {
						logger.error(Access_exception);	
					}
					BigDecimal price_bought = algorithm.getPriceBought();

					double gain = price.doubleValue()
							- price_bought.doubleValue();

					gain = gain / price_bought.doubleValue();

					algorithm.setPriceBought(BigDecimal.ZERO);
					algorithm.setAbsoluteGain(
							algorithm.getAbsoluteGain() + gain);

					algorithm.setAggregateGain(
							algorithm.getAggregateGain() * (gain));

				}

			}

		}
	}
	public void setAlgorithms(Stock stock) {
		List<Algorithm> algorithms = new ArrayList<>();
		listawrapper.getSimpleMovingAverages().values().forEach(
				algo -> algorithms.add(new Algorithm(stock, algo.getName())));
		stock.setAlgorithms(algorithms);

	}
}
