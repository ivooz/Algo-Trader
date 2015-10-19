package com.gft.service.updating;

import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import org.apache.commons.lang3.time.DateUtils;
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

	@Autowired
	StockRepository stockRepository;
	@Autowired
	AlgorithmRepository algorithmRepository;
	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepository;

	public void saveAlgorithmStatistics() {
		algorithmHistoryRepository.save(saveAlgorithmStatisticsWithDate(stockRepository.findAllAndFetchAllAlgorithmsEagerly(),
				DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH)));
	}

	public void saveAlgorithmStatistics(Date date, String ticker) {
		algorithmHistoryRepository.save(saveAlgorithmStatisticsWithDate(Arrays.asList(
				stockRepository.findByIdAndFetchAlgorithmsEagerly(ticker)), date));
	}

	private List<AlgorithmHistory> saveAlgorithmStatisticsWithDate(List<Stock> stocks, Date date) {
		List<AlgorithmHistory> algorithmHistories = new ArrayList<>();
		algorithmRepository.save(stocks.stream()
						.map(Stock::getAlgorithms)
						.flatMap(l -> l.stream())
						.map(algorithm -> {
							algorithmHistories.add(new AlgorithmHistory(algorithm, date,algorithm.getAggregateGain(),
									algorithm.getAbsoluteGain()));
							algorithm.setAbsoluteGain(0);
							algorithm.setAggregateGain(0);
							return algorithm;
						}).collect(Collectors.toList())
		);
		return algorithmHistories;
	}
}
