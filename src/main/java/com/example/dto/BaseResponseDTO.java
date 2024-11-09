package com.example.dto;

import com.example.constant.RESPONSE_STATUS;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"status", "message"})
@Schema(
        title = "Ответ на запрос. Базовый класс с данными о статусе ответа и текстом ошибки (при наличии)"
)
public class BaseResponseDTO {
    @Schema(
            title = "Статус ответа (success, error)",
            example = "error"
    )
    @JsonProperty("status")
    private final String status;

    @Schema(
            title = "Сообщение об ошибке (при статусе = ERROR)",
            example = "Ошибка при валидации данных запроса"
    )
    @JsonProperty("message")
    private final String message;

    public BaseResponseDTO(
            String status,
            String message
    ) {
        this.status = status;
        this.message = message;
    }

    public BaseResponseDTO() {
        this.status = RESPONSE_STATUS.SUCCESS.getStatus();
        this.message = null;
    }
}
