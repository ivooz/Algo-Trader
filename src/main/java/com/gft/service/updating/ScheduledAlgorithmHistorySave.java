package com.gft.service.updating;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
		List<AlgorithmHistory> listOfHistories = new ArrayList<>();
		Iterator<Stock> it = stockRepo.findAllAndFetchAllAlgorithmsEagerly()
				.iterator();
		while (it.hasNext()) {
			Iterator<Algorithm> stockAlgorithms = it.next().getAlgorithms()
					.iterator();
			while (stockAlgorithms.hasNext()) {
				Algorithm algorithm = stockAlgorithms.next();
				Date today = new Date();
				today.setHours(0);
				today.setMinutes(0);
				today.setSeconds(0);
				AlgorithmHistory algorithmHistory = new AlgorithmHistory(
						algorithm, today, algorithm.getAggregateGain(),
						algorithm.getAbsoluteGain());

				algorithm.setAbsoluteGain(0);
				algorithm.setAggregateGain(0);
				algorithm.setPriceBought(BigDecimal.ZERO);
				algorithmRepo.save(algorithm);
				algorithmRepo.flush();
				listOfHistories.add(algorithmHistory);
			}
		}
		algorithmHistoryRepo.save(listOfHistories);
		algorithmHistoryRepo.flush();
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

				algorithm = new Algorithm(algorithm.getStock(),
						algorithm.getName());
				algorithmRepo.save(algorithm);
				algorithmRepo.flush();
				listOfHistories.add(algorithmHistory);
			}
		}
		algorithmHistoryRepo.save(listOfHistories);
		algorithmHistoryRepo.flush();
	}
}
