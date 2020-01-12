package com.oi.financialoilservices.dto;

import com.oi.financialoilservices.entity.Oil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOilTransactionDto implements Serializable {

    private long transactionId;

    private long volume;

    private BigDecimal price;

    private String operation;

    private Oil oil;

    private LocalDateTime transactionDateTime;

    private BigDecimal revenueYield;

    private BigDecimal priceEarningsRatio;

    private BigDecimal volumeWeightedOilPrice;

}
