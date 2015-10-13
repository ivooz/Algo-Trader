package com.gft.service;

import com.gft.model.db.StockHistory;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public interface DataDownloadService {

    /**
     * http://real-chart.finance.yahoo.com/table.csv?s={{ticker}}&d=9&e=13&f=2015&g=d&a=7&b=9&c=1996&ignore=.csv
     * @param ticker
     * @return
     */
    public List<StockHistory> downloadHistoricalData(String ticker);

    /**
     * http://finance.yahoo.com/webservice/v1/symbols/{{ticker}}/quote?format=json&view=detail
     * @param ticker
     * @return
     */
    public StockHistory downloadCurrentData(String ticker);
}
