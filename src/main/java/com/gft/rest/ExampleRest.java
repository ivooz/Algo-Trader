package com.gft.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.gft.model.db.Stock;
import com.gft.repository.data.StockRepository;

/**
 * Created by iozi on 06/10/2015.
 */
@RestController
public class ExampleRest {
 @Autowired
 static StockRepository st;

}
