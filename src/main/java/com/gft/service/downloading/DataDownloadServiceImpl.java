package com.gft.service.downloading;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.service.DataAccessException;
import com.gft.service.parsing.ParsingException;
import com.gft.service.parsing.StockHistoryCsvConverter;
import com.gft.service.parsing.StockHistoryJsonConverter;
import com.gft.service.parsing.StockJsonConverter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
@Service
public class DataDownloadServiceImpl implements DataDownloadService {

    private static final Logger logger = LoggerFactory.getLogger(DataDownloadService.class);

    @Value("${data.history.url.prefix}")
    private String historyUrlPrefix;

    @Value("${data.history.url.suffix}")
    private String historyUrlSuffix;

    @Value("${data.current.url.prefix}")
    private String currentDataUrlPrefix;

    @Value("${data.current.url.suffix}")
    private String currentDataUrlSuffix;

    @Autowired
    private StockHistoryCsvConverter stockHistoryCsvConverter;

    @Autowired
    private StockHistoryJsonConverter jsonConverter;

    @Autowired
    private StockJsonConverter stockJsonConverter;

    private StringBuilder urlBuilder = new StringBuilder();

    @Override
    public List<StockHistory> downloadHistoricalData(Stock stock) throws DataAccessException {
        logger.info("Obtaining historical data");
        try {
            List<StockHistory> historyList = stockHistoryCsvConverter.convertToStockHistory(
                    loadStockInfo(historyUrlPrefix, historyUrlSuffix, stock.getTicker()));
            historyList.parallelStream().forEach(h -> h.setStock(stock));
            return historyList;
        } catch (IOException | ParsingException ex) {
            logger.error("Failed to obtain historical stock data",ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public StockHistory downloadCurrentData(Stock stock) throws DataAccessException {
        logger.info("Obtaining current stock data");
        try {
            StockHistory stockHistory = jsonConverter.fromJson(loadStockInfo(currentDataUrlPrefix,
                    currentDataUrlSuffix, stock.getTicker()));
            stockHistory.setStock(stock);
            return stockHistory;
        } catch (IOException ex) {
            logger.error("Failed to obtain current stock data",ex);
            throw new DataAccessException(ex);
        }
    }

    @Override
    public Stock downloadNewStock(String ticker) throws DataAccessException {
        logger.info("Obtaining new stock data");
        try {
            Stock stock = stockJsonConverter.fromJson(loadStockInfo(currentDataUrlPrefix, currentDataUrlSuffix, ticker));
            return stock;
        } catch (IOException ex) {
            logger.error("Failed to obtain new stock data", ex);
            throw new DataAccessException(ex);
        }
    }

    private String loadStockInfo(String prefix, String suffix, String ticker) throws IOException {
        urlBuilder.setLength(0);
        URL stockDataUrl = new URL(urlBuilder.append(prefix).append(ticker).append(suffix).toString());
        try(InputStream contentStream = stockDataUrl.openStream()) {
            return IOUtils.toString(contentStream);
        }
    }
}
