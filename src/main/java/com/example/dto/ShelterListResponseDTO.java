package com.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@JsonPropertyOrder({"status", "message", "items"})
@Schema(
        title = "Ответ на запрос списка приютов"
)
public class ShelterListResponseDTO extends BaseResponseDTO {
    @ArraySchema(
            uniqueItems = true,
            schema = @Schema(
                    implementation = ShelterDTO.class
            )
    )
    @JsonProperty("items")
    private final List<ShelterDTO> data;
}
