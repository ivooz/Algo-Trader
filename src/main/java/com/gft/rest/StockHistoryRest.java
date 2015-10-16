package com.gft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;

@RestController
public class StockHistoryRest {

	@Autowired
	StockHistoryRepository stockHistoryRepository;
	@Autowired
	StockRepository stockRepository;

	@RequestMapping(value = "/history/{ticker}")
	public List<StockHistory> getStockHistory(@PathVariable("ticker") String ticker) {

//		Stock testStock = new Stock();
//		testStock.setFullName("KGHM");
//		testStock.setTicker("KGH");
//
//		StockHistory stockHistory = new StockHistory();
//		stockHistory.setId(1l);
//		stockHistory.setDate(new Date());
//		stockHistory.setStock(testStock);
//		stockHistory.setOpeningPrice(new BigDecimal(46.30));
//		stockHistory.setClosingPrice(new BigDecimal(47.40));
//		stockHistory.setLowPrice(new BigDecimal(46.20));
//		stockHistory.setHighPrice(new BigDecimal(47.60));
//		stockHistory.setVolume(123l);
//
//		stockRepository.save(testStock);
//		stockHistoryRepository.save(stockHistory);

		List<StockHistory> historyOfStock = new ArrayList<StockHistory>(
				stockHistoryRepository.findByStockOrderByDateDesc(stockRepository.findOne(ticker)));

		return historyOfStock;
	}
}
