package com.gft.repository;

import com.gft.model.db.Stock;
import com.gft.model.db.StockHistory;

import java.util.ArrayList;

/**
 * Created by iozi on 13/10/2015.
 */
public interface HistoryDAO {

    public ArrayList<StockHistory> obtainHistoryForPeriod(Stock stock, int days);
}
