package com.oi.financialoilservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InputOilDto implements Serializable {

    private String oilId;

    private String oilTypeId;

    private int fixedRevenue;

    private int variableRevenue;

    private BigDecimal oilBarrelValue;

}
