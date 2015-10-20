package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.aspect.Log;
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
    @RequestMapping(value = "/stock/{ticker}")
    public String getStockHistory(@PathVariable("ticker") String ticker) {
    	Gson gson = new Gson();
        try {
            if(stockRepo.exists(ticker))
            {
            return gson.toJson(stockRepo.FindStockWithoutAlgorithms(ticker));
            	
            }
            else{
            	
            	newStockService.addNewStock(ticker);
            	
            }
        } catch (InsufficientDataException e) {
            //TODO Exception handling
            e.printStackTrace();
        } catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return gson.toJson(stockRepo.FindStockWithoutAlgorithms(ticker));
    }
}
