package com.gft.service;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public interface DataDownloadService {

    /**
     * http://real-chart.finance.yahoo.com/table.csv?s={{ticker}}&d=9&e=13&f=2015&g=d&a=7&b=9&c=1996&ignore=.csv
     * @param stock
     * @return
     */
    public List<StockHistory> downloadHistoricalData(Stock stock) throws DataAccessException;

    /**
     * http://finance.yahoo.com/webservice/v1/symbols/{{ticker}}/quote?format=json&view=detail
     * @param stock
     * @return
     */
    public StockHistory downloadCurrentData(Stock stock) throws DataAccessException;
}
