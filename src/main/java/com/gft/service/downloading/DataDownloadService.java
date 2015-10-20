package com.gft.service.downloading;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.service.DataAccessException;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public interface DataDownloadService {

    public List<StockHistory> downloadHistoricalData(Stock stock) throws DataAccessException;

    public StockHistory downloadCurrentData(Stock stock) throws DataAccessException;

    public Stock downloadNewStock(String ticker) throws DataAccessException;
}
