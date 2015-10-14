package com.gft.service;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.service.parsing.ParsingException;
import com.gft.service.parsing.StockHistoryCsvConverter;
import com.gft.service.parsing.StockHistoryJsonConverter;
import org.apache.commons.io.IOUtils;
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

    private StringBuilder urlBuilder = new StringBuilder();

    @Override
    public List<StockHistory> downloadHistoricalData(Stock stock) throws DataAccessException {
        try {
            List<StockHistory> historyList = stockHistoryCsvConverter.convertToStockHistory(
                    loadStockInfo(historyUrlPrefix,historyUrlSuffix,stock));
            historyList.parallelStream().forEach(h -> h.setStock(stock));
            return historyList;
        } catch (IOException | ParsingException ex) {
            throw new DataAccessException(ex);
        }
    }

    @Override
    public StockHistory downloadCurrentData(Stock stock) throws DataAccessException {
        try {
            StockHistory stockHistory = jsonConverter.fromJson(loadStockInfo(currentDataUrlPrefix,
                    currentDataUrlSuffix,stock));
            stockHistory.setStock(stock);
            return stockHistory;
        } catch (IOException ex) {
            throw new DataAccessException(ex);
        }
    }

    private String loadStockInfo(String prefix, String suffix, Stock stock) throws IOException {
        urlBuilder.setLength(0);
        URL stockDataUrl = new URL(urlBuilder.append(prefix).append(stock.getTicker())
                .append(suffix).toString());
        try(InputStream contentStream = stockDataUrl.openStream()) {
            return IOUtils.toString(contentStream);
        }
    }
}
