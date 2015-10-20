package com.gft.repository.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gft.model.db.Algorithm;
import com.gft.model.db.Stock;

/**
 * Created by iozi on 13/10/2015.
 */
@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, String> {
	
	@Query("SELECT  a.stock, a.name, a.aggregateGain, a.absoluteGain, a.priceBought FROM Algorithm a WHERE a.stock = (:stock)")
	public List<String> findByStock(@Param("stock")Stock stock);
	@Query("SELECT a FROM Algorithm a WHERE a.name = (:name)")
	public Algorithm findByName(@Param("name")String name);
	@Query("SELECT  a FROM Algorithm a WHERE a.stock.ticker = (:Stockname)")
	public List<Algorithm> findByTicker(@Param("Stockname")String ticker);
	@Query("SELECT  a FROM Algorithm a WHERE a.name = (:name) AND a.stock.ticker =(:ticker)")
	public Algorithm findByNameandTicker(@Param("name")String name,@Param("ticker")String ticker);


	@Query("SELECT a FROM Algorithm a LEFT JOIN FETCH a.algorithmHistories WHERE a.stock.ticker = (:ticker)")
	List<Algorithm> findByStockTicker(@Param("ticker") String ticker);
}
