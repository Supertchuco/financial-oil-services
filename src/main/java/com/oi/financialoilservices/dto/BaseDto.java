package com.oi.financialoilservices.dto;

import com.oi.financialoilservices.exception.InputDataException;

import java.lang.reflect.Field;

import static java.util.Objects.isNull;

public class BaseDto {

    public void validate(final Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (isNull(field)) {
                throw new InputDataException();
            }
        }
    }
}
