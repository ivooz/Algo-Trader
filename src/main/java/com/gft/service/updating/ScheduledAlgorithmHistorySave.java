package com.gft.service.updating;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;
@Service
public class ScheduledAlgorithmHistorySave {

	@Autowired
	AlgorithmHistoryRepository algorithmHistoryRepo;
	@Autowired
	StockRepository stockRepo;
	@Autowired
	AlgorithmRepository algorithmRepo;
	public void saveAlgorithmStatistics() {
		saveAlgorithmStatisticsWithDate(DateUtils.truncate(new Date(),
				java.util.Calendar.DAY_OF_MONTH));

	}
	public void saveAlgorithmStatisticsWithDate(Date date) {
		List<AlgorithmHistory> listOfHistories = new ArrayList<>();
		Iterator<Stock> it = stockRepo.findAllAndFetchAllAlgorithmsEagerly()
				.iterator();
		while (it.hasNext()) {
			Iterator<Algorithm> stockAlgorithms = it.next().getAlgorithms()
					.iterator();
			while (stockAlgorithms.hasNext()) {
				Algorithm algorithm = stockAlgorithms.next();

				AlgorithmHistory algorithmHistory = new AlgorithmHistory(
						algorithm, date, algorithm.getAggregateGain(),
						algorithm.getAbsoluteGain());
				algorithm.setAbsoluteGain(0);
				algorithm.setAggregateGain(0);
				algorithm.setPriceBought(BigDecimal.ZERO);
				algorithmRepo.save(algorithm);
				algorithmRepo.flush();
				algorithmRepo.save(algorithm);
				algorithmRepo.flush();
				listOfHistories.add(algorithmHistory);
			}
		}
		algorithmHistoryRepo.save(listOfHistories);
		algorithmHistoryRepo.flush();
	}
	public void saveAlgorithmStatisticsForSpecificTicker(Date date,
			String Ticker) {
		List<AlgorithmHistory> listOfHistories = new ArrayList<>();
		Stock stock = stockRepo.findByIdAndFetchAlgorithmsEagerly("MSFT");

		Iterator<Algorithm> stockAlgorithms = stock.getAlgorithms().iterator();
		while (stockAlgorithms.hasNext()) {
			Algorithm algorithm = stockAlgorithms.next();

			AlgorithmHistory algorithmHistory = new AlgorithmHistory(algorithm,
					date, algorithm.getAggregateGain(),
					algorithm.getAbsoluteGain());
			algorithm.setAbsoluteGain(0);
			algorithm.setAggregateGain(0);
			algorithm.setPriceBought(BigDecimal.ZERO);
			algorithmRepo.save(algorithm);
			algorithmRepo.flush();
			algorithmRepo.save(algorithm);
			algorithmRepo.flush();
			listOfHistories.add(algorithmHistory);
		}

		algorithmHistoryRepo.save(listOfHistories);
		algorithmHistoryRepo.flush();
	}
}
