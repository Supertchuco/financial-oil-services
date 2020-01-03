package com.oi.financialoilservices.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Operations {

    BUY("Buy operation"),
    SELL("Sell operation");

    private String operation;

}
