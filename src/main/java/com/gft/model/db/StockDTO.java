package com.gft.model.db;

public class StockDTO {
	private String ticker;
	private String fullName;
	public String getTicker() {
		return ticker;
	}
	public StockDTO() {
		super();
	}
	public StockDTO(String ticker, String fullName) {
		super();
		this.ticker = ticker;
		this.fullName = fullName;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
