package com.oi.financialoilservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "OilTransaction")
@Table(name = "OilTransaction")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class Oil {

    @Id
    @Column
    private String oidId;

    @Column
    private String type;

    @Column
    private int fixedRevenue;

    @Column
    private int variableRevenue;

    @Column
    private long oilBarrelValue;
}
