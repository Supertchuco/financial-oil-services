package com.oi.financialoilservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InputOilTransactionDto implements Serializable {

    @NotNull
    private String operation;

    @NotNull
    private long volume;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String oilId;

}
