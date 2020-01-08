package com.oi.financialoilservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity(name = "OilType")
@Table(name = "OilType")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class OilType implements Serializable {

    @Id
    @Column
    private String oilType;

    @Column
    private String description;

}