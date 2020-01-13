package com.oi.financialoilservices.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGeometricMeanDto implements Serializable {

    private BigDecimal value;

}
