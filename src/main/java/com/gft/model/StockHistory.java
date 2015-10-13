package com.gft.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
public class StockHistory {

    private final Date date;
    private final Stock stock;
    private final BigDecimal openingPrice;
    private final BigDecimal closingPrice;
    private final BigDecimal lowPrice;
    private final BigDecimal highPrice;
    private final long volume;

    public StockHistory(Date date, Stock stock, BigDecimal openingPrice, BigDecimal closingPrice, BigDecimal lowPrice, BigDecimal highPrice, long volume) {
        this.date = date;
        this.stock = stock;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.volume = volume;
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    public long getVolume() {
        return volume;
    }
}
