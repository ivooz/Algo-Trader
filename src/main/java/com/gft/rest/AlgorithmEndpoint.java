package com.gft.rest;

import com.gft.aspect.Log;
import com.gft.aspect.LogNoArgs;
import com.gft.component.PredicitonAlgorithmsWrapper;
import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import com.gft.repository.data.AlgorithmHistoryRepository;
import com.gft.repository.data.AlgorithmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/algorithm")
public class    AlgorithmEndpoint {

    @Autowired
    AlgorithmHistoryRepository algorithmHistoryRepository;

    @Autowired
    AlgorithmRepository algorithmRepository;

    @Autowired
    PredicitonAlgorithmsWrapper algorithmsWrapper;

    @Log
    @RequestMapping(value = "/{name}")
    public Algorithm getByName(@PathVariable("name") String name) {
        return algorithmRepository.findByName(name);
    }

    @Log
    @RequestMapping(value = "/{name}/{ticker}")
    public Algorithm getByNameAndTicker(@PathVariable("name") String name, @PathVariable("ticker") String ticker) {
        return algorithmRepository.findByNameandTicker(name, ticker);
    }

    @LogNoArgs
    @RequestMapping(value = "/{name}/{ticker}/history")
    public List<AlgorithmHistory> getHistoryByNameAndTicker(@PathVariable("name") String name,
                                                            @PathVariable("ticker") String ticker) {
        return algorithmHistoryRepository.findByTickerAndAlgorithmName(name,
                ticker);
    }

    @LogNoArgs
    @RequestMapping(value = "/names")
    public Set<String> getAllNames() {
        return algorithmsWrapper.getAlgorithms().keySet();
    }
}
