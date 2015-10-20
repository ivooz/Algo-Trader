package com.gft.rest;

import com.gft.aspect.Log;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(StockEndpoint.class);

    @Autowired
    AlgorithmRepository algorithmRepository;

    @Autowired
    NewStockService newStockService;

    @Autowired
    StockRepository stockRepository;

    @Log
    @RequestMapping(value = "/")
    public List<Stock> getALl() {
        return stockRepository.findAll();
    }

    @Log
    @RequestMapping(value = "/{ticker}", method = RequestMethod.GET)
    public Stock getByTicker(@PathVariable("ticker") String ticker) {
        return stockRepository.findOne(ticker);
    }

    @Log
    @RequestMapping(value = "/{ticker}", method = RequestMethod.POST)
    public Stock postNewStock(@PathVariable("ticker") String ticker) {
        try {
            if (stockRepository.exists(ticker)) {
                return stockRepository.findOne(ticker);
            } else {
                newStockService.addNewStock(ticker);
            }
        } catch (InsufficientDataException | DataAccessException ex) {
            logger.error("Unable to create new stock!", ex);
        }
        return stockRepository.findOne(ticker);
    }

    @Log
    @RequestMapping(value = "/{ticker}/algorithms")
    public List<Algorithm> getAlgorithmsByTicker(@PathVariable("ticker") String ticker) {
        return algorithmRepository.findByTicker(ticker);
    }
}
