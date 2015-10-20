package com.gft.service.updating;

import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import javafx.util.Pair;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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

	public void saveAlgorithmStatistics() {
		Pair<List<AlgorithmHistory>,List<Algorithm>> result = saveAlgorithmStatisticsWithDate(stockRepository.findAllAndFetchAllAlgorithmsEagerly(),
				DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH));
		algorithmHistoryRepository.save(result.getKey());
		algorithmRepository.save(result.getValue());
	}

	public void saveAlgorithmStatistics(Date date, String ticker) {
		Pair<List<AlgorithmHistory>,List<Algorithm>> result = saveAlgorithmStatisticsWithDate(Arrays.asList(
				stockRepository.findByIdAndFetchAlgorithmsEagerly(ticker)), date);
		algorithmHistoryRepository.save(result.getKey());
		algorithmRepository.save(result.getValue());
	}

	private Pair<List<AlgorithmHistory>,List<Algorithm>> saveAlgorithmStatisticsWithDate(List<Stock> stocks, Date date) {
		List<AlgorithmHistory> algorithmHistories = new ArrayList<>();
		List<Algorithm> algorithms = new ArrayList<>();
		algorithmRepository.save(stocks.stream()
						.map(Stock::getAlgorithms)
						.flatMap(l -> l.stream())
						.map(algorithm -> {
							algorithmHistories.add(new AlgorithmHistory(algorithm, date,algorithm.getAggregateGain(),
									algorithm.getAbsoluteGain()));
							algorithms.add(algorithm);
							algorithm.setAbsoluteGain(0);
							algorithm.setAggregateGain(0);
							return algorithm;
						}).collect(Collectors.toList())
		);
		return new Pair<>(algorithmHistories,algorithms);
	}
}
