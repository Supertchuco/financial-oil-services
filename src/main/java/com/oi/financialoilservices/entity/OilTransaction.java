package com.oi.financialoilservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "OilTransaction")
@Table(name = "OilTransaction")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings({"PMD.ImmutableField", "PMD.ShortVariable"})
public class OilTransaction {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private long volume;

    @Column
    private long price;

    @Column
    @CreationTimestamp
    private LocalDateTime transactionDateTime;
}
