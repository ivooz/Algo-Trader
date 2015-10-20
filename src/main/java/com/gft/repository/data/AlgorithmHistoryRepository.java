package com.gft.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gft.model.db.AlgorithmHistory;
import com.gft.model.db.Stock;

/**
 * Created by iozi on 13/10/2015.
 */
public interface AlgorithmHistoryRepository extends JpaRepository<AlgorithmHistory, Long> {

	@Query("SELECT a FROM AlgorithmHistory a WHERE a.algorithm.name = (:name) AND a.algorithm.stock.ticker = (:ticker)")
	public List<AlgorithmHistory> findByTickerAndAlgorithmName(@Param("name")String name,@Param("ticker")String ticker);

}
