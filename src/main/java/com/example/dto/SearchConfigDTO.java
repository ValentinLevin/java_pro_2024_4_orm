package com.example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(
        title = "Параметры запроса данных: фильтрация и сортировка"
)
public class SearchConfigDTO {
    @Schema(
            title = "Параметры фильтрации",
            implementation = FilterParamGroupDTO.class
    )
    private final FilterParamGroupDTO filterParams;

    @ArraySchema(
            uniqueItems = true,
            schema = @Schema(
                    implementation = SortParamDTO.class
            )
    )
    private final List<SortParamDTO> sortParams;

    @Schema(
            title = "Номер страницы",
            defaultValue = "1"
    )
    @Positive(message = "The value of pageNumber should be positive")
    private final int pageNumber;

    @Schema(
            title = "Размер страницы",
            defaultValue = "10"
    )
    @PositiveOrZero(message = "The value of pageSize should be positive or zero")
    private final int pageSize;

    @JsonCreator
    public SearchConfigDTO(
            @JsonProperty("filterParams") FilterParamGroupDTO filterParams,
            @JsonProperty("sortParams") List<SortParamDTO> sortParams,
            @JsonProperty(value = "pageNumber") Integer pageNumber,
            @JsonProperty(value = "pageSize") Integer pageSize
    ) {
        this.filterParams = filterParams;
        this.sortParams = sortParams;
        this.pageNumber = pageNumber == null ? 1 : pageNumber;
        this.pageSize = pageSize == null ? 10 : pageSize;
    }
}
