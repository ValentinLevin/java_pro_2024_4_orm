package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonPropertyOrder({"status", "message", "data"})
@Schema(
        title = "Ответ на запрос данных одного приюта"
)
public class SingleShelterResponseDTO extends BaseResponseDTO {
    @Schema(
            name = "data",
            description = "Данные приюта",
            implementation = ShelterDTO.class
    )
    @JsonProperty("data")
    private final ShelterDTO data;
}
