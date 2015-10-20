package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.service.DataAccessException;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public interface HistoryDAO {

    String INSUFFICIENT_INTERVAL = "Requested interval is bigger than available history";

    /**
     *
     * @param stock
     * @param days including the current day (5 = 4 previous days + today)
     * @return
     */
    List<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) throws InsufficientDataException, DataAccessException;

    /**
     * Invoke to obtain the date of the latest day in the history (i.e. "today")
     *
     * @return
     */
    StockHistory getCurrentDay(Stock stock) throws InsufficientDataException, DataAccessException;

    /**
     * @param stock
     * @return size of the available history i.e. number of days
     * @throws InsufficientDataException
     * @throws DataAccessException
     */
    int getHistorySize(Stock stock) throws InsufficientDataException, DataAccessException;


}
