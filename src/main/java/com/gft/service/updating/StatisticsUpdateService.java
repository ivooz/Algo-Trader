package com.gft.service.updating;

import com.gft.component.PredicitonAlgorithmsWrapper;
import com.gft.model.Action;
import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;

@Service
public class StatisticsUpdateService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsUpdateService.class);

    @Autowired
    PredicitonAlgorithmsWrapper listawrapper;

    @Autowired
    StockTransactionService stockTransactionService;

    public void updateStatistics(Stock stock, Date date, HistoryDAO historyDAO) {
        BigDecimal price = null;
        Iterator<Algorithm> it = stock.getAlgorithms().iterator();
        while (it.hasNext()) {
            Algorithm algorithm = it.next();
            Action action = listawrapper.getAlgorithms().get(algorithm.getName()).predict(date, stock, historyDAO);
            switch (action) {
                case BUY:
                    stockTransactionService.buy(stock, historyDAO, algorithm);
                    break;
                case SELL:
                    stockTransactionService.sell(stock, historyDAO, algorithm);
                    break;
            }
        }
    }
}
