package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
import com.gft.model.db.Stock;
import com.gft.repository.data.InsufficientDataException;
import com.gft.repository.data.StockRepository;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import com.google.gson.Gson;

/**
 * Created by iozi on 19/10/2015.
 */
@RestController
public class NewTickerRest {

    @Autowired
    NewStockService newStockService;
    @Autowired
    StockRepository stockRepo;

    @Log
    @RequestMapping(value = "/stock/{ticker}", method = RequestMethod.GET)
    public Stock getStockHistory(@PathVariable("ticker") String ticker) {
        return stockRepo.findOne(ticker);
    }

    @Log
    @RequestMapping(value = "/stock/{ticker}", method = RequestMethod.POST)
    public Stock posttockHistory(@PathVariable("ticker") String ticker) {
        try {
            if (stockRepo.exists(ticker)) {
                return stockRepo.findOne(ticker);
            } else {
                newStockService.addNewStock(ticker);
            }
        } catch (InsufficientDataException | DataAccessException e) {
            //TODO Exception handling
            e.printStackTrace();
        }
        return stockRepo.findOne(ticker);
    }
}
