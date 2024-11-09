package com.example.dto;

import com.example.model.PET_TYPE;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
@Schema(
        title = "Данные приюта"
)
public class ShelterDTO {
    @Schema(
            title = "Идентификатор приюта",
            example = "6730a0374a524518f713fc75"
    )
    @JsonProperty("id")
    private final String id;

    @Schema(
            title = "Название приюта",
            example = "Приют для животных 'Домик'"
    )
    @JsonProperty("name")
    @NotBlank(message = "Name cannot be null")
    private final String name;

    @Schema(
            title = "Местоположение приюта",
            example = "г. Астана, ул. Ауэзова, д. 1"
    )
    @JsonProperty("location")
    @NotBlank(message = "Location cannot be null")
    private final String location;

    @Schema(
            title = "Вместимость приюта",
            example = "100"
    )
    @JsonProperty("capacity")
    @Positive(message = "Capacity must be positive")
    private final Integer capacity;

    @Schema(
            title = "Тип животного, которые содержатся в приюте",
            example = "CAT"
    )
    @JsonProperty("type")
    @NotNull(message = "Type cannot be null")
    private final PET_TYPE type;

    @Schema(
            title = "Рейтинг приюта",
            example = "4.5"
    )
    @JsonProperty("rating")
    @PositiveOrZero(message = "Rating must be positive")
    private final Double rating;

    @Schema(
            title = "Признак финансирования приюта государством",
            example = "true"
    )
    @JsonProperty("isGovernmentFunded")
    @NotNull(message = "isGovernmentFunded cannot be null")
    private final Boolean isGovernmentFunded;

    @Schema(
            title = "Среднее время содержания животного (в днях)",
            example = "30"
    )
    @PositiveOrZero(message = "Average adoption time must be positive")
    @JsonProperty("averageAdoptionTime")
    private final Integer averageAdoptionTime;

    @Schema(
            title = "Дневная стоимость содержания животного",
            example = "100.0"
    )
    @JsonProperty("dailyCost")
    @PositiveOrZero(message = "Daily cost must be positive")
    private final Double dailyCost;

    @JsonCreator()
    public ShelterDTO(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("location") String location,
            @JsonProperty("capacity") Integer capacity,
            @JsonProperty("type") PET_TYPE type,
            @JsonProperty("rating") Double rating,
            @JsonProperty("isGovernmentFunded") Boolean isGovernmentFunded,
            @JsonProperty("averageAdoptionTime") Integer averageAdoptionTime,
            @JsonProperty("dailyCost") Double dailyCost
    ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.type = type;
        this.rating = rating;
        this.isGovernmentFunded = isGovernmentFunded;
        this.averageAdoptionTime = averageAdoptionTime;
        this.dailyCost = dailyCost;
    }
}
