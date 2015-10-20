package com.gft.repository.data;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
public interface StockRepository extends JpaRepository<Stock, String> {

	@Query("SELECT s.ticker FROM Stock s")
	@Fetch(FetchMode.SELECT)
	List<String> findTickers();

	@Query("SELECT s FROM Stock s LEFT JOIN FETCH s.algorithms al WHERE s.ticker = (:ticker)")
	Stock findByIdAndFetchAlgorithmsEagerly(@Param("ticker") String ticker);

	@Query("SELECT s FROM Stock s LEFT JOIN FETCH s.algorithms")
	List<Stock> findAllAndFetchAllAlgorithmsEagerly();
	@Query("SELECT s.ticker, s.fullName, s.type FROM Stock s WHERE s.ticker = (:ticker)")
	String FindStockWithoutAlgorithms(@Param("ticker") String ticker);
	@Query("SELECT s.ticker, s.fullName, s.type FROM Stock s")
	List<String> FindStocksWithoutAlgorithms();
	
}
