package com.oi.financialoilservices.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "OilTransaction")
@Table(name = "OilTransaction")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class OilTransaction implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private long transactionId;

    @Column
    private long volume;

    @Column
    private BigDecimal price;

    @Column
    private String operation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "oilId")
    @JsonManagedReference
    private Oil oil;

    @Column
    @CreationTimestamp
    private LocalDateTime transactionDateTime;

    public OilTransaction(final long volume, final BigDecimal price, final String operation, final Oil oil) {
        this.volume = volume;
        this.price = price;
        this.operation = operation;
        this.oil = oil;
    }
}
