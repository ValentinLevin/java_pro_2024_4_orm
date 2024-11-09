package com.example.constant;

import com.example.exception.SearchParamsParseException;
import lombok.Getter;

@Getter
public enum SORT_DIRECTION {
    ASC("asc"),
    DESC("desc");

    private final String value;

    public static SORT_DIRECTION fromValue(String value) {
        for (SORT_DIRECTION sortDirection : SORT_DIRECTION.values()) {
            if (sortDirection.value.equalsIgnoreCase(value)) {
                return sortDirection;
            }
        }
        throw new SearchParamsParseException("sortDirection", value);
    }

    SORT_DIRECTION(String value) {
        this.value = value;
    }
}
