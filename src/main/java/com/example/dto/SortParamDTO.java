package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
        title = "Параметр сортировки"
)
public class SortParamDTO {
    @Schema(
            description = "Наименование поля для сортировки",
            example = "name"
    )
    private final String key;

    @Schema(
            description = "Направление сортировки (asc/desc)",
            example = "asc"
    )
    private final String sortDirection;

    public SortParamDTO(
            String key,
            @JsonProperty(value = "sort", defaultValue = "") String sortDirection
    ) {
        this.key = key;
        this.sortDirection = sortDirection;
    }
}
