package com.gft.rest;

import com.gft.aspect.LogNoArgs;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StockHistoryEndpoint {

	@Autowired
	StockHistoryRepository stockHistoryRepository;
	@Autowired
	StockRepository stockRepository;

	@LogNoArgs
	@RequestMapping(value = "/history/{ticker}")
	public List<StockHistory> getStockHistory(@PathVariable("ticker") String ticker) {
		List<StockHistory> historyOfStock = new ArrayList<StockHistory>(
				stockHistoryRepository.findByStockOrderByDateDesc(stockRepository.findOne(ticker)));
		return historyOfStock;
	}
}
