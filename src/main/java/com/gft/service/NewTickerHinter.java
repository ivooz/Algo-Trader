package com.gft.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.gft.model.db.Stock;
import com.gft.repository.data.StockRepository;
import com.gft.service.parsing.ParsingException;
import com.google.gson.Gson;
@Service
public class NewTickerHinter {

	private static final Logger logger = LoggerFactory
			.getLogger(NewTickerHinter.class);
	@Autowired
	StockRepository sr;
	public static final String FAILED_TO_READ_CSV = "Failed to read CSV";
	public List<String> ReadAllAvailableStocks() throws ParsingException {

		logger.info("Reading List of All Tickers from CSV file");

		String File = "src/main/resources/Data/companylist.csv";
		List<String> AvailableTickers = new ArrayList<String>();

		final String[] header = new String[]{"Ticker", null, null, null,
				null, null, null, null, null};

		final CellProcessor[] processors = new CellProcessor[]{
				new NotNull(), null, null, null, null, null, null, null,
				null};

		Stock customer;
		try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(File),
				CsvPreference.STANDARD_PREFERENCE);) {

			beanReader.getHeader(true);
			while ((customer = beanReader.read(Stock.class, header,
					processors)) != null) {
		
				AvailableTickers.add(customer.getTicker());

		
			}
		}  catch (IOException e) {
			logger.error(FAILED_TO_READ_CSV,e);
            throw new ParsingException(FAILED_TO_READ_CSV,e);	
		}

		return AvailableTickers;
	}

	public List<String> getTickersAdded() {

		return sr.findTickers();

	}
	public String HintNotPickedTickers() {
		List<String> TickersNotPicked = null;
		try {
			TickersNotPicked = new ArrayList<String>(
					ReadAllAvailableStocks());
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TickersNotPicked.removeAll(getTickersAdded());
		String json = new Gson().toJson(TickersNotPicked);
		return json;
	}
}
