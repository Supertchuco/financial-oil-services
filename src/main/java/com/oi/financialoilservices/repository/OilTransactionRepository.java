package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.OilTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OilTransactionRepository extends CrudRepository<OilTransaction, Long> {

    @Query("select oilTransaction from OilTransaction oilTransaction where oilTransaction.transactionDateTime < sysdate - 1/(24*60)")
    List<OilTransaction> findAllWithTransactionDateTimeUntil30Minutes();
}
