package com.gft.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.aspect.LogNoArgs;
import com.gft.service.downloading.NewTickerHinter;
import com.gft.service.parsing.ParsingException;

@RestController
public class TickersRest {

	@Autowired
	NewTickerHinter tickerHinter;
	
	@Log
	@RequestMapping(value = "/tickers", method = RequestMethod.GET)
	public List<String> getTickersOfExistingStocks() {
		return tickerHinter.getTickersOfExistingStocksInDB();
	}

	@LogNoArgs
	@RequestMapping(value = "/tickersAvailable", method = RequestMethod.GET)
	public List<String> getTickersOfNotExistingStocks() {
		try {
			return tickerHinter.hintNotPickedTickers();
		} catch (ParsingException e) {
			return new ArrayList<String>();
		}
	}
}
