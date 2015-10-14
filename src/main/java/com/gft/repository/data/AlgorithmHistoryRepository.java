package com.gft.repository.data;

import com.gft.model.db.Algorithm;
import com.gft.model.db.AlgorithmHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by iozi on 13/10/2015.
 */
@Repository
public interface AlgorithmHistoryRepository extends JpaRepository<AlgorithmHistory, Long> {
}