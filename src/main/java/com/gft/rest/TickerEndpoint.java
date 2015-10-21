package com.gft.rest;


import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.aspect.Log;
import com.gft.aspect.LogNoArgs;
import com.gft.aspect.LogNoArgs;
import com.gft.model.db.StockDTO;
import com.gft.service.downloading.NewTickerHinter;
import com.gft.service.downloading.NewTickerHinter;
import com.gft.service.parsing.ParsingException;
import com.gft.service.parsing.ParsingException;


@RestController
public class TickerEndpoint {

	private static final Logger logger = LoggerFactory.getLogger(TickerEndpoint.class);

	@Autowired
	NewTickerHinter tickerHinter;

	@Log
	@RequestMapping(value = "/tickers", method = RequestMethod.GET)
	public List<StockDTO> getTickersOfExistingStocks() {
		return tickerHinter.getStocksDTOInDB();
	}

	@LogNoArgs
	@RequestMapping(value = "/tickersAvailable", method = RequestMethod.GET)
	public List<StockDTO> getTickersOfNotExistingStocks() {
		try {
			return tickerHinter.hintNotPickedTickers();


		} catch (ParsingException ex) {
			logger.error("Unable to obtain available ticker!", ex);
			return new ArrayList<>();


		}
	}
}
