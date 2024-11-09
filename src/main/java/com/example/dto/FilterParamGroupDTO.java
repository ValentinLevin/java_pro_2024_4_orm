package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(
        title = "Группы выражений фильтрации при запросе данных",
        description = "Используется для разделения групп параметров. " +
                "Например (name='First shelter' or name='Second shelter) and (capacity < 10 or capacity > 50). " +
                "Каждое из выражений в скобках - это FilterParamGroupDTO"
)
public class FilterParamGroupDTO {
    @Schema(
            title = "Логическое соединение между выражениями в группе (and/or)",
            example = "and"
    )
    private final String joinBetween;

    @Schema(
            title = "Список полей с требованиями к значениям"
    )
    private final List<FilterParamDTO> filterParams;

    @Schema(
            title = "Список групп выражений вложенных в текущую группу (при сложной логике и дополнительной вложенности условий фильтрации)"
    )
    private final List<FilterParamGroupDTO> filterParamGroups;

    public boolean isFilterParamsExists() {
        return this.filterParams != null || this.filterParamGroups != null;
    }

    public FilterParamGroupDTO(
            @JsonProperty(value = "joinBetween", defaultValue = "and") String joinBetween,
            @JsonProperty("expressions") List<FilterParamDTO> filterParams,
            @JsonProperty("expressionGroups") List<FilterParamGroupDTO> filterParamGroups
    ) {
        this.joinBetween = joinBetween;
        this.filterParams = filterParams;
        this.filterParamGroups = filterParamGroups;
    }
}
