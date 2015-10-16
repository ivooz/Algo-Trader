package com.gft.scheduling;

import com.gft.aspect.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by iozi on 14/10/2015.
 */
@Component
public class StockUpdateJob {

    private static final Logger logger = LoggerFactory.getLogger(StockUpdateJob.class);

    // TODO: uncomment when implemented
//    @Autowired
//    DailyUpdateService dailyUpdateService;

    /**
     * Runs every day at midnight
     * //TODO take stock market opening and closing time into account
     */
    @Log
    @Scheduled(cron="0 0 0 * * *")
    public void updateStockData() {
        logger.info("Invoking daily stock update");
//        dailyUpdateService.updateStocks();
    }
}
