package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;
import com.gft.repository.data.InsufficientDataException;
import com.gft.service.DataAccessException;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
public interface HistoryDAO {

    public static final String INSUFFICIENT_INTERVAL = "Requested interval is bigger than available history";

    /**
     *
     * @param stock
     * @param days including the current day (5 = 4 previous days + today)
     * @return
     */
    List<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days) throws InsufficientDataException, DataAccessException, InvalidArgumentException;
}
