package com.gft.service;

import java.util.ArrayList;
import java.util.List;

import com.gft.service.parsing.StockCsvConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.repository.data.StockRepository;
import com.gft.service.parsing.ParsingException;
import com.google.gson.Gson;

@Service
public class NewTickerHinter {

    private static final Logger logger = LoggerFactory.getLogger(NewTickerHinter.class);

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockCsvConverter stockCsvConverter;

    public List<String> getTickersAdded() {
        return stockRepository.findTickers();
    }

    public String hintNotPickedTickers() throws ParsingException {
        List<String> availableTickers = stockCsvConverter.readAllAvailableStocks();
        availableTickers.removeAll(getTickersAdded());
        return new Gson().toJson(availableTickers);
    }
}
