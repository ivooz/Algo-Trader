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

import junit.framework.Assert;

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

		Generator gen = new Generator();
		st.save(gen.GenerateStocks(10));

		Assert.assertNotNull(st.findAll());
	}
}