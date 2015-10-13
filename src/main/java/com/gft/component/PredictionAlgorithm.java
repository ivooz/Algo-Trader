package com.gft.component;

import com.gft.model.Action;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;

import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
public interface PredictionAlgorithm {

    /**
     * Computes advice for stock for a given day
     * @param date
     * @param stock
     * @param historyDAO
     * @return
     */
    public Action predict(Date date, Stock stock, HistoryDAO historyDAO);
}
