package com.gft.repository.data;

import com.gft.model.db.AlgorithmHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iozi on 13/10/2015.
 */
public interface AlgorithmHistoryRepository extends JpaRepository<AlgorithmHistory, Long> {
	
}
