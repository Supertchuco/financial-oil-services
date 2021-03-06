package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.OilTransaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OilTransactionRepository extends CrudRepository<OilTransaction, Long> {

    OilTransaction findByTransactionId(Long transactionId);

    List<OilTransaction> findAll();

    List<OilTransaction> findByOilOilTypeOilTypeAndTransactionDateTimeBetween(String oilType,
                                                                              LocalDateTime transactionDateStart, LocalDateTime transactionDateEnd);

    @Query("select distinct oilTransaction.price from OilTransaction oilTransaction")
    List<BigDecimal> findAllPricesInAllOilTransactions();
}
