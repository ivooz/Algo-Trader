package com.gft.service;

import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;

import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
public interface StatisticsUpdateService {

    /**
     * Updates algorith standings for a given stock in a give day
     * @param stock
     * @param date
     * @param historyDAO source of history records
     */
    public void updateStatistics(Stock stock, Date date, HistoryDAO historyDAO);
}
