package com.gft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;

public class Generator {

    public static List<Algorithm> generateAlgorithms(int count) {
        List<Algorithm> algs = new ArrayList<Algorithm>();
        String name = null;
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            name = new Float(ran.nextFloat()).toString();
            Algorithm alg = new Algorithm(new Stock(), name);
            algs.add(alg);
        }
        return algs;
    }

    public static List<Stock> generateStocks(int count) {
        List<Stock> stocks = new ArrayList<Stock>();
        String name = null;
        Random ran = new Random();
        for (int i = 0; i < count; i++) {
            name = new Float(ran.nextFloat()).toString();
            Stock stock = new Stock();
            stock.setFullName(name);
            stock.setTicker(name.substring(0, 4));
            stocks.add(stock);
        }
        return stocks;
    }
}


