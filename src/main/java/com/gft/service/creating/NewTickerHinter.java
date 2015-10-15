package com.gft.service.creating;

import com.gft.repository.data.StockRepository;
import com.gft.service.parsing.ParsingException;
import com.gft.service.parsing.StockCsvConverter;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
