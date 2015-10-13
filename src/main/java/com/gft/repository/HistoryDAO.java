package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;

import java.util.ArrayList;

/**
 * Created by iozi on 13/10/2015.
 */
public interface HistoryDAO {

    /**
     *
     * @param stock
     * @param days including the current day (5 = 4 previous days + today)
     * @return
     */
    public ArrayList<StockHistory> obtainStockHistoryForPeriod(Stock stock, int days);
}
