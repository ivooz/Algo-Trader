package com.gft.service.updating;

import com.gft.model.db.Stock;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.service.DataAccessException;
import com.gft.service.downloading.DataDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by iozi on 15/10/2015.
 */
@Service
public class StockUpdateService {

    @Autowired
    private StockHistoryRepository stockHistoryRepository;

    @Autowired
    private DataDownloadService downloadService;

    /**
     * Obtains current stock data and saves it
     *
     * @param stock
     */
    public void updateStock(Stock stock) throws DataAccessException {
        stockHistoryRepository.save(downloadService.downloadCurrentData(stock));
    }
}
