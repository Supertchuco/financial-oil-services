package com.oi.financialoilservices.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    UNEXPECTED_ERROR("Unexpected error happened, please call application support for more details"),
    ERROR_OIL_REGISTRY_NOT_FOUND_ON_DATABASE("The requested oil registry with ID specified was not found"),
    ERROR_PERSIST_OIL_REGISTRY_ON_DATABASE("Unexpected error happened in save oil registry on database, please call application support for more details"),

    ERROR_PERSIST_OIL_TRANSACTION_REGISTRY_ON_DATABASE("Unexpected error happened in save oil transaction registry on database, please call application support for more details");

    private String message;

}
