package com.example.constant;

import lombok.Getter;

@Getter
public enum RESPONSE_STATUS {
    SUCCESS("success"),
    ERROR("error");

    private final String status;

    RESPONSE_STATUS(String status) {
        this.status = status;
    }
}
