package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.OilTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OilTransactionRepository extends CrudRepository<OilTransaction, Long> {
}
