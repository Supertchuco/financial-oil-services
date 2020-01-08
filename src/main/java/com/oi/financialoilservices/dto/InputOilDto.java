package com.oi.financialoilservices.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.oi.financialoilservices.entity.OilType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class InputOilDto implements Serializable {

    private String id;

    private String oilTypeId;

    private int fixedRevenue;

    private int variableRevenue;

    private BigDecimal oilBarrelValue;
}
