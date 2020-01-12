package com.oi.financialoilservices.repository;

import com.oi.financialoilservices.entity.OilTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OilTransactionRepository extends CrudRepository<OilTransaction, Long> {

    OilTransaction findByTransactionId(final Long transactionId);

    List<OilTransaction> findAll();

    List<OilTransaction> findByOilOilTypeOilTypeAndTransactionDateTimeBetween(final String OilType, final LocalDateTime transactionDateStart, final LocalDateTime transactionDateEnd);
}
