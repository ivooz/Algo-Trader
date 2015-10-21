package com.gft.service.updating;

import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.DatabaseHistoryDao;
import com.gft.repository.HistoryDAO;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AlgorithmHistoryUpdateService {

	private static final Logger logger = LoggerFactory.getLogger(AlgorithmHistoryUpdateService.class);

	@Autowired
	StockRepository stockRepository;

	@Autowired
	AlgorithmRepository algorithmRepository;

	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	@Autowired
	StockTransactionService stockTransactionService;

	@Autowired
	DatabaseHistoryDao databaseHistoryDao;

	public void saveAlgorithmStatistics() {
		algorithmHistoryRepository.save(saveAlgorithmStatisticsWithDate(stockRepository.findAllAndFetchAllAlgorithmsEagerly(),
				DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
	}

	public void saveAlgorithmStatistics(Date date, Stock stock) {
		algorithmHistoryRepository.save(saveAlgorithmStatisticsWithDate(Arrays.asList(stock), date));
	}

	private List<AlgorithmHistory> saveAlgorithmStatisticsWithDate(List<Stock> stocks, Date date) {
		sellCurrentStocks(stocks);
		List<AlgorithmHistory> algorithmHistories = new ArrayList<>();
		algorithmRepository.save(stocks.stream()
						.map(Stock::getAlgorithms)
						.flatMap(l -> l.stream())
						.map(algorithm -> {
							algorithmHistories.add(new AlgorithmHistory(algorithm, date, algorithm.getAggregateGain(),
									algorithm.getAbsoluteGain()));
							algorithm.setAbsoluteGain(0);
							algorithm.setAggregateGain(1);
							return algorithm;
						}).collect(Collectors.toList())
		);
		rebuyCurrentStocks(stocks);
		return algorithmHistories;
	}

	private void sellCurrentStocks(List<Stock> stocks) {
		stocks.parallelStream().forEach(stock -> {
			stock.getAlgorithms().forEach(algorithm -> {
				stockTransactionService.sell(stock, databaseHistoryDao, algorithm);
			});
		});
	}

	private void rebuyCurrentStocks(List<Stock> stocks) {
		stocks.parallelStream().forEach(stock -> {
			stock.getAlgorithms().forEach(algorithm -> {
				stockTransactionService.buy(stock, databaseHistoryDao, algorithm);
			});
		});
	}

	private double calculateGain(double price, double price_bought) {
		double gain = price - price_bought;
		gain = gain / price_bought;
		return gain;
	}
}
