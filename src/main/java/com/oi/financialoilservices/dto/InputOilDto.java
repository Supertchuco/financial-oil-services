package com.oi.financialoilservices.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class InputOilDto extends BaseDto implements Serializable {

    private String id;

    private String oilTypeId;

    private int fixedRevenue;

    private int variableRevenue;

    private BigDecimal oilBarrelValue;

}
