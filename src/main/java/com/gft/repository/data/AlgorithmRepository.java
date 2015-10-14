package com.gft.repository.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.model.db.Algorithm;

/**
 * Created by iozi on 13/10/2015.
 */
@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, String> {
}
