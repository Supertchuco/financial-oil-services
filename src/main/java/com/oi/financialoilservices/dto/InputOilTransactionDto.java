package com.oi.financialoilservices.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InputOilTransactionDto implements Serializable {

    private String operation;

    private long volume;

    private BigDecimal price;

    private String oilId;

}
