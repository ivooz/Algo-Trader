package com.gft.rest;

import com.gft.aspect.Log;
import com.gft.repository.data.InsufficientDataException;
import com.gft.service.DataAccessException;
import com.gft.service.creating.NewStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iozi on 19/10/2015.
 */
@RestController
public class NewTickerRest {

    @Autowired
    NewStockService newStockService;

    @Log
    @RequestMapping(value = "/stock/{ticker}")
    public void getStockHistory(@PathVariable("ticker") String ticker) {
        try {
            newStockService.addNewStock(ticker);
        } catch (DataAccessException | InsufficientDataException e) {
            //TODO Exception handling
            e.printStackTrace();
        }
    }
}
