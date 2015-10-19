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
}
