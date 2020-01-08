package com.oi.financialoilservices.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity(name = "Oil")
@Table(name = "Oil")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class Oil implements Serializable {

    @Id
    @Column
    private String oilId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oilType")
    @JsonManagedReference
    private OilType oilType;

    @Column
    private int fixedRevenue;

    @Column
    private int variableRevenue;

    @Column
    private BigDecimal oilBarrelValue;

    public Oil(final String oilId, final OilType oilType, final int fixedRevenue, final BigDecimal oilBarrelValue) {
        this.oilId = oilId;
        this.oilType = oilType;
        this.fixedRevenue = fixedRevenue;
        this.oilBarrelValue = oilBarrelValue;
    }
}
