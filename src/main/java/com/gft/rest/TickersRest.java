package com.gft.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gft.service.NewTickerHinter;
import com.gft.service.parsing.ParsingException;

@RestController
public class TickersRest {

	@Autowired
	NewTickerHinter tickerHinter;

	@RequestMapping(value = "/ExistingStocksInDB", method = RequestMethod.GET)
	public List<String> getTickersOfExistingStocks() {
		return tickerHinter.getTickersOfExistingStocksInDB();
	}

	@RequestMapping(value = "/NotExistingStockInDB", method = RequestMethod.GET)
	public List<String> getTickersOfNotExistingStocks() {
		try {
			return tickerHinter.hintNotPickedTickers();
		} catch (ParsingException e) {
			return new ArrayList<String>();
		}
	}

}
