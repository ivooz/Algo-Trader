package com.gft.service.parsing;

import com.gft.aspect.Log;
import com.gft.aspect.LogNoArgs;
import com.gft.model.db.StockHistory;
import com.jayway.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by iozi on 13/10/2015.
 */
@Service
public class StockHistoryJsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(StockHistoryJsonConverter.class);

    @LogNoArgs
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
