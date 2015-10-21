package com.gft.service.downloading;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.model.db.Stock;
import com.gft.model.db.StockDTO;
import com.gft.repository.data.StockRepository;
import com.gft.service.parsing.ParsingException;
import com.gft.service.parsing.StockCsvConverter;

@Service
public class NewTickerHinter {

	private static final Logger logger = LoggerFactory
			.getLogger(NewTickerHinter.class);

	@Autowired
	StockRepository stockRepository;

	@Autowired
	StockCsvConverter stockCsvConverter;

	public List<StockDTO> getStocksDTOInDB() {
		return stockRepository.FindStocksDTOWithoutAlgorithms();

	}

	public List<StockDTO> hintNotPickedTickers() throws ParsingException {
		List<StockDTO> availableTickers = stockCsvConverter
				.readAllAvailableStocks();
		List<StockDTO> dtos = getStocksDTOInDB();
		List<StockDTO> todelete = new ArrayList<>();
		Iterator<StockDTO> itgiven = dtos.iterator();
		while (itgiven.hasNext()) {
			StockDTO given = itgiven.next();
			Iterator<StockDTO> itavailable = availableTickers.iterator();
			while (itavailable.hasNext()) {
				StockDTO available = itavailable.next();
				if (available.getTicker().equals(given.getTicker())) {
					todelete.add(available);
				}
			}

		}
		availableTickers.removeAll(todelete);

		return availableTickers;
	}
}
