package com.gft.service.creating;

import com.gft.repository.InsufficientDataException;
import com.gft.service.DataAccessException;

/**
 * Created by iozi on 13/10/2015.
 */
public interface NewStockService {

    public void addNewStock(String ticker) throws DataAccessException, InsufficientDataException;
}
