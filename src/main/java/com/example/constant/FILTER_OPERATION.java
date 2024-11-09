package com.example.constant;

import com.example.exception.SearchParamsParseException;
import lombok.Getter;

@Getter
public enum FILTER_OPERATION {
    EQUALS("eq"),
    CONTAINS("in"),
    LESS_THAN("lt"),
    GREATER_THAN("gt"),;

    private final String operation;

    public static FILTER_OPERATION fromValue(String text) {
        for (FILTER_OPERATION item : FILTER_OPERATION.values()) {
            if (item.operation.equalsIgnoreCase(text)) {
                return item;
            }
        }
        throw new SearchParamsParseException("operation", text);
    }

    FILTER_OPERATION(String operation) {
        this.operation = operation;
    }
}
