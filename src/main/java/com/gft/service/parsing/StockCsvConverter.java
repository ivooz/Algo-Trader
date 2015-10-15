package com.gft.service.parsing;

import com.gft.model.db.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iozi on 14/10/2015.
 */
@Service
public class StockCsvConverter {

    private static final Logger logger = LoggerFactory.getLogger(StockCsvConverter.class);

    private static final String FAILED_TO_READ_CSV = "Failed to read CSV";
    public static final String CSV_PATH = "src/main/resources/Data/companylist.csv";

    public List<String> readAllAvailableStocks() throws ParsingException {
        logger.info("Reading List of All Tickers from CSV file");
        List<String> availableTickers = new ArrayList<String>();
        final String[] header = new String[]{"Ticker", null, null, null, null, null, null, null, null};
        final CellProcessor[] processors = new CellProcessor[]{new NotNull(), null, null, null, null, null, null, null, null};
        Stock customer;

        try (ICsvBeanReader beanReader = new CsvBeanReader(new FileReader(CSV_PATH), CsvPreference.STANDARD_PREFERENCE)) {
            beanReader.getHeader(true);
            while ((customer = beanReader.read(Stock.class, header, processors)) != null) {
                availableTickers.add(customer.getTicker());
            }
        } catch (IOException e) {
            logger.error(FAILED_TO_READ_CSV, e);
            throw new ParsingException(FAILED_TO_READ_CSV, e);
        }
        return availableTickers;
    }
}
