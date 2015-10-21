package com.gft.service.updating;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.HistoryDAO;
import com.gft.repository.InsufficientDataException;
import com.gft.service.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by iozi on 21/10/2015.
 */
@Service
public class StockTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(StockTransactionService.class);
    private static final Logger transactionsLogger = LoggerFactory.getLogger("transactions");
    public static final String Access_exception = "DATA COULD NOT BE ACCESSED";

    public void buy(Stock stock, HistoryDAO historyDAO, Algorithm algorithm) {
        BigDecimal price = null;
        if (algorithm.getPriceBought().equals(BigDecimal.ZERO)) {
            try {
                price = historyDAO.getCurrentDay(stock).getClosingPrice();
            } catch (InsufficientDataException | DataAccessException e) {
                logger.error(Access_exception);
            }
            try {
                transactionsLogger.info(new StringBuilder().append(historyDAO.getCurrentDay(stock).getDate())
                        .append(" Transaction for ").append(algorithm.getName()).append(" bought ")
                        .append(stock.getTicker()).append(" for ").append(price).toString());
            } catch (InsufficientDataException | DataAccessException e) {
            }
            algorithm.setPriceBought(price);
        }
    }

    public void sell(Stock stock, HistoryDAO historyDAO, Algorithm algorithm) {
        BigDecimal price = null;
        if (!(algorithm.getPriceBought().equals(BigDecimal.ZERO))) {
            try {
                price = historyDAO.getCurrentDay(stock).getClosingPrice();
            } catch (InsufficientDataException | DataAccessException e) {
                logger.error(Access_exception);

            }
            double gain = calculateGain(price.doubleValue(), algorithm.getPriceBought().doubleValue());
            try {
                transactionsLogger.info(new StringBuilder().append(historyDAO.getCurrentDay(stock).getDate())
                        .append(" Transaction for ").append(algorithm.getName()).append(" sold ").append(stock.getTicker())
                        .append(" for ").append(price.doubleValue()).append(" which was bought for ")
                        .append(algorithm.getPriceBought().doubleValue()).append(" and earned ")
                        .append(gain).append("%.").toString());
            } catch (InsufficientDataException | DataAccessException e) {
            }
            algorithm.setPriceBought(BigDecimal.ZERO);
            algorithm.setAbsoluteGain(algorithm.getAbsoluteGain() + gain);
            algorithm.setAggregateGain(algorithm.getAggregateGain() * (1 + gain));
        }
    }

    private double calculateGain(double price, double price_bought) {
        double gain = price - price_bought;
        gain = gain / price_bought;
        return gain;
    }
}
