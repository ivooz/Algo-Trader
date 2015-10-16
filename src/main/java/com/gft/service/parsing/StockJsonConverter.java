package com.gft.service.parsing;

import com.gft.aspect.Log;
import com.gft.aspect.LogNoArgs;
import com.gft.model.db.Stock;
import com.jayway.restassured.path.json.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by iozi on 15/10/2015.
 */
@Service
public class StockJsonConverter {

    private static final Logger logger = LoggerFactory.getLogger(StockJsonConverter.class);

    @LogNoArgs
    public Stock fromJson(String json) {
        JsonPath jsonPath = new JsonPath(json);
        Stock stock = new Stock();
        stock.setTicker(jsonPath.getString("list.resources[0].resource.fields.symbol"));
        stock.setFullName(jsonPath.getString("list.resources[0].resource.fields.name"));
        stock.setType(jsonPath.getString("list.resources[0].resource.fields.type"));
        return stock;
    }
}
