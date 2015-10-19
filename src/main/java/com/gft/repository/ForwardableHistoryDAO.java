package com.gft.repository;

/**
 * Created by iozi on 16/10/2015.
 */
public interface ForwardableHistoryDAO extends HistoryDAO {

    /**
     * Pushes history into the future by one day
     */
    void forwardHistory();
}
