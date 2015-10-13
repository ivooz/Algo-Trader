package com.gft.service;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;

import java.util.Date;
import java.util.Map;

/**
 * Created by iozi on 13/10/2015.
 */
public interface ScoreCountingService {

    /**
     * Computes scores for all algorithms in a stock for a given day
     * @param stock
     * @param date
     * @param historyDAO source of history records
     * @return
     */
    public Map<Algorithm,Integer> computeScore(Stock stock, Date date, HistoryDAO historyDAO);
}
