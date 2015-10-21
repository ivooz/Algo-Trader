package com.gft.service.removing;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.StockHistoryRepository;
import com.gft.repository.data.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by iozi on 20/10/2015.
 */
@Service
public class StockRemovingService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    StockHistoryRepository stockHistoryRepository;

    @Autowired
    AlgorithmHistoryRepository algorithmHistoryRepository;

    @Transactional
    public void remove(String ticker) {
        Stock stock = stockRepository.findByIdAndFetchAlgorithmsEagerly(ticker);
        stockHistoryRepository.deleteByStock(stock);
        for(Algorithm algorithm : stock.getAlgorithms()) {
            algorithmHistoryRepository.deleteByAlgorithm(algorithm);
        }
        stockRepository.delete(stock);
    }
}
