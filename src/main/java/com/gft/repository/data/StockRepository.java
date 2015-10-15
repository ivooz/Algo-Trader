package com.gft.repository.data;

import com.gft.model.db.Stock;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by iozi on 13/10/2015.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
	public static final String Querys = "SELECT s.ticker FROM Stock s";
	@Query(Querys)
	@Fetch(FetchMode.SELECT)
	public List<String> findTickers();
}
