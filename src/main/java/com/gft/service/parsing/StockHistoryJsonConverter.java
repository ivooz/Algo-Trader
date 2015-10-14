package com.gft.service.parsing;

import com.gft.model.db.StockHistory;
import com.jayway.restassured.path.json.JsonPath;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
@Service
public class StockHistoryJsonConverter {

    public StockHistory fromJson(String json) {
        JsonPath jsonPath = new JsonPath(json);
        StockHistory stockHistory = new StockHistory();
        stockHistory.setClosingPrice(new BigDecimal(jsonPath.getDouble("list.resources[0].resource.fields.price")));
        BigDecimal openingPrice = stockHistory.getClosingPrice().subtract(
                new BigDecimal(jsonPath.getDouble("list.resources[0].resource.fields.change")));
        stockHistory.setOpeningPrice(openingPrice);
        stockHistory.setHighPrice(new BigDecimal(jsonPath.getDouble("list.resources[0].resource.fields.day_high")));
        stockHistory.setLowPrice(new BigDecimal(jsonPath.getDouble("list.resources[0].resource.fields.day_low")));
        stockHistory.setVolume(jsonPath.getLong("list.resources[0].resource.fields.volume"));
        stockHistory.setDate(new Date());
        return stockHistory;
    }

}
