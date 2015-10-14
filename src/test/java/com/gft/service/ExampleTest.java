package com.gft.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gft.config.Application;
import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmRepository;
import com.gft.repository.data.StockRepository;

/**
 * Created by iozi on 06/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
public class ExampleTest {
	@Autowired
	 private JdbcTemplate namedJdbcTemplate;
	@Autowired
	StockRepository st;
	@Autowired
	AlgorithmRepository ar;
    @Test
    public void test() throws ClassNotFoundException {
    Stock stock = new Stock();
    stock.setFullName("Pies");
    stock.setTicker("pi");
    Stock stock1 = new Stock();
    stock1.setFullName("Pies1");
    stock1.setTicker("pi1");
    Stock stock2 = new Stock();
    stock2.setFullName("Pies2");
    stock2.setTicker("pi2");
    st.save(stock);
    st.save(stock2);
    st.save(stock1);
Algorithm alg = new Algorithm(stock2, "LIczyciel");
Algorithm alg2 = new Algorithm(stock1, "Liczydlo");
Algorithm alg3 =  new Algorithm(stock1, "Kalkulador");
Algorithm alg4 = new Algorithm(stock, "Liczbownik");
Algorithm alg5 = new Algorithm(stock, "Cyfrator");

List<Algorithm> algs = new ArrayList<Algorithm>();
algs.add(alg);
algs.add(alg2);
algs.add(alg3);
algs.add(alg4);
algs.add(alg5);
ar.save(alg);
ar.save(alg2);
ar.save(alg3);
ar.save(alg4);
ar.save(alg5);
ar.flush();
stock.setAlgorithms(algs);

st.flush();
Iterator <Stock> it = st.findAll().iterator();
while(it.hasNext())
System.out.println(it.next().getFullName());
    }
}