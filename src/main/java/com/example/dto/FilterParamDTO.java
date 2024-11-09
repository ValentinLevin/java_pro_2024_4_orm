package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(
        title = "Требование к данным поля"
)
public class FilterParamDTO {
    @Schema(
            title = "Наименование поля",
            example = "capacity"
    )
    @NotBlank(message = "Field key cannot be null or empty")
    private final String key;

    @Schema(
            title = "Операция сравнения (равно-eq, больше-gt, меньше-lt, содержит-in)",
            example = "eq"
    )
    @NotBlank(message = "Field operation cannot be null or empty")
    private final String operation;

    @Schema(
            title = "Значение с которым производится сравнение",
            example = "100"
    )
    @NotNull(message = "Field value cannot be null")
    private final Object value;

    public FilterParamDTO(
            @JsonProperty("key") String key,
            @JsonProperty("operation") String operation,
            @JsonProperty("value") Object value
    ) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
