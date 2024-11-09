package com.example.constant;

import com.example.exception.SearchParamsParseException;
import lombok.Getter;

@Getter
public enum JOIN {
    AND("and"),
    OR("or"),
    OR_NOT("or not"),
    AND_NOT("and not");

    private final String value;

    public static JOIN fromValue(String value) {
        for (JOIN item : JOIN.values()) {
            if (item.value.equalsIgnoreCase(value)) {
                return item;
            }
        }
        throw new SearchParamsParseException("joinBetween", value);
    }

    JOIN(String value) {
        this.value = value;
    }
}
