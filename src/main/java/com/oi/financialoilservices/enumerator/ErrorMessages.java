package com.oi.financialoilservices.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {

    UNEXPECTED_ERROR("Unexpected error happened, please call application support for more details"),

    ERROR_OIL_REGISTRY_NOT_FOUND_ON_DATABASE("The requested oil registry with ID specified was not found"),
    ERROR_PERSIST_OIL_REGISTRY_ON_DATABASE("Unexpected error happened in save oil registry on database"),
    ERROR_GET_OIL_REGISTRY_ON_DATABASE("Unexpected error happened in get oil registry on database"),

    ERROR_PERSIST_OIL_TRANSACTION_REGISTRY_ON_DATABASE("Unexpected error happened in save oil transaction registry on database"),
    ERROR_INVALID_OIL_TRANSACTION_OPERATION("The requested oil transaction operation is invalid"),

    ERROR_OIL_TYPE_REGISTRY_NOT_FOUND_ON_DATABASE("The requested oil type registry with ID specified was not found");

    private String message;

}
