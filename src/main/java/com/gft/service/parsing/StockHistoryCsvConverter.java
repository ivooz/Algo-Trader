package com.gft.service.parsing;

import com.gft.model.db.StockHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ParseBigDecimal;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseLong;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iozi on 14/10/2015.
 */
@Service
public class StockHistoryCsvConverter {

    private static final Logger logger = LoggerFactory.getLogger(StockHistoryCsvConverter.class);

    private static final CellProcessor[] PROCESSORS = new CellProcessor[]{
            new ParseDate("yyyy-MM-dd"), // Date
            new ParseBigDecimal(), // Open
            new ParseBigDecimal(), // High
            new ParseBigDecimal(), // Low
            new ParseBigDecimal(), // Close
            new ParseLong(), // Volume
            null
    };

    private static final String[] FIELD_MAPPING = {"date","openingPrice","highPrice","lowPrice","closingPrice","volume",null};
    public static final String FAILED_TO_READ_CSV = "Failed to read CSV";

    public List<StockHistory> convertToStockHistory(String csv) throws ParsingException {
        logger.info("Converting CSV into list of StockHistory");
        ArrayList<StockHistory> historyList = new ArrayList<>();
        try(ICsvBeanReader beanReader = new CsvBeanReader(new StringReader(csv), CsvPreference.STANDARD_PREFERENCE)) {
            String[] header = beanReader.getHeader(true);
            StockHistory stockHistory;
            while ((stockHistory = beanReader.read(StockHistory.class, FIELD_MAPPING, PROCESSORS)) != null) {
                historyList.add(stockHistory);
            }
        } catch (IOException ex) {
            logger.error(FAILED_TO_READ_CSV,ex);
            throw new ParsingException(FAILED_TO_READ_CSV,ex);
        }
        return historyList;
    }
}
