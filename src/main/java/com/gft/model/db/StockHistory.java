package com.gft.model.db;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */

@Entity
public class StockHistory {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private Date date;
    @OneToOne(targetEntity=Stock.class)
    private Stock stock;
    private BigDecimal openingPrice;
    private BigDecimal closingPrice;
    private BigDecimal lowPrice;
    private BigDecimal highPrice;
    private long volume;

    public StockHistory() {
    }

    public StockHistory(Date date, Stock stock, BigDecimal openingPrice, BigDecimal closingPrice, BigDecimal lowPrice, BigDecimal highPrice, long volume) {
        this.date = date;
        this.stock = stock;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        this.volume = volume;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
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

    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    public BigDecimal getHighPrice() {
        return highPrice;
    }

    public long getVolume() {
        return volume;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    public void setLowPrice(BigDecimal lowPrice) {
        this.lowPrice = lowPrice;
    }

    public void setHighPrice(BigDecimal highPrice) {
        this.highPrice = highPrice;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }	@Override
	public String toString() {
		return "StockHistory [id=" + id + ", date=" + date + ", stock=" + stock + ", openingPrice=" + openingPrice
				+ ", closingPrice=" + closingPrice + ", lowPrice=" + lowPrice + ", highPrice=" + highPrice + ", volume="
				+ volume + "]";
	}
    
}
