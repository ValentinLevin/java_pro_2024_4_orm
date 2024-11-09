package com.example.controller;

import com.example.dto.*;
import com.example.service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Validated
@RestController
@RequestMapping("/shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @Operation(
            tags = "Операции с приютами",
            summary = "Добавление данных о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Запрос успешно обработан",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = SingleShelterResponseDTO.class
                                    )
                            )
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<SingleShelterResponseDTO> addShelter(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные о приюте",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = ShelterDTO.class
                            )
                    ),
                    required = true
            )
            @Valid @RequestBody ShelterDTO request,
            HttpServletRequest httpServletRequest,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        SingleShelterResponseDTO response = shelterService.createShelter(request);
        URI location = uriComponentsBuilder
                .path(httpServletRequest.getRequestURI())
                .path("/{id}")
                .buildAndExpand(response.getData().getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @Operation(
            tags = "Операции с приютами",
            summary = "Получение полного списка приютов",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос успешно обработан, данные сформированы и отправлены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ShelterListResponseDTO.class
                                    )
                            )
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<ShelterListResponseDTO> getAllShelters() {
        return ResponseEntity.ok(shelterService.findAllShelters());
    }

    @Operation(
            tags = "Операции с приютами",
            summary = "Поиск данных о приютах по параметрам",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос успешно обработан, данные о приюте удалены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ShelterListResponseDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Приют с указанным id не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BaseResponseDTO.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/search")
    public ResponseEntity<ShelterListResponseDTO> search(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Параметры поиска и сортировки результатов поиска",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = SearchConfigDTO.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "search by name",
                                            description = "фильтр по полю name с частичным совпадением, чтобы можно было искать, например, по подстроке",
                                            summary = "Поиск по названию приюта",
                                            value = "{ \"pageNumber\": 1, \"pageSize\": 10, \"filterParams\": { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"name\", \"operation\": \"in\", \"value\": \"sh\" } ], \"expressionGroups\": [ { \"joinBetween\": \"or\", \"expressions\": [ { \"key\": \"name\", \"operation\": \"in\", \"value\": \"d\" }, { \"key\": \"name\", \"operation\": \"in\", \"value\": \"f\" } ] } ] }, \"sortParams\": [ { \"key\": \"name\", \"sort\":\"desc\" }, { \"key\": \"id\", \"sort\":\"asc\" } ] }"
                                    ),
                                    @ExampleObject(
                                            name = "search by type and location",
                                            description = "по полям type и location. Для type мы будем использовать точное совпадение (чтобы искать конкретно \"для собак\", \"для кошек\" и т. д.), а для location — частичное совпадение (поиск по городу или району)",
                                            summary = "Фильтр по типу и местоположению",
                                            value = "{ \"pageNumber\": 1, \"pageSize\": 10, \"filterParams\": { \"joinBetween\": \"and\", \"expressionGroups\": [ { \"joinBetween\": \"or\", \"expressions\": [ { \"key\": \"location\", \"operation\": \"eq\", \"value\": \"almaty\" }, { \"key\": \"location\", \"operation\": \"eq\", \"value\": \"karaganda\" } ] }, { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"type\", \"operation\": \"eq\", \"value\": \"CAT\" } ] } ] }, \"sortParams\": [ { \"key\": \"name\", \"sort\":\"desc\" }, { \"key\": \"id\", \"sort\":\"asc\" } ] }"
                                    ),
                                    @ExampleObject(
                                            name = "search by range",
                                            description = "для таких полей, как capacity, rating, averageAdoptionTime и dailyCost. Нам нужно, чтобы можно было задать диапазон значений, например, вместимость от 50 до 100 мест или рейтинг от 4 и выше",
                                            summary = "Фильтр по диапазонам значений",
                                            value = "{ \"pageNumber\": 1, \"pageSize\": 10, \"filterParams\": { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"type\", \"operation\": \"eq\", \"value\": \"CAT\" } ], \"expressionGroups\": [ { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"capacity\", \"operation\": \"gt\", \"value\": 10 }, { \"key\": \"capacity\", \"operation\": \"lt\", \"value\": 90 } ] }, { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"rating\", \"operation\": \"gt\", \"value\": 2 }, { \"key\": \"rating\", \"operation\": \"lt\", \"value\": 6 } ] } ] }, \"sortParams\": [ { \"key\": \"rating\", \"sort\":\"desc\" }, { \"key\": \"name\", \"sort\":\"asc\" } ] }"
                                    ),
                                    @ExampleObject(
                                            name = "logical operators",
                                            description = "чтобы фильтры поддерживали AND, OR, AND NOT, OR NOT. Например, можно искать приюты с рейтингом не ниже 4, которые находятся не в определенном районе, и с вместимостью более 50",
                                            summary = "Логические операторы",
                                            value = "{ \"pageNumber\": 1, \"pageSize\": 10, \"filterParams\": { \"joinBetween\": \"and\", \"expressionGroups\": [ { \"joinBetween\": \"or\", \"expressions\": [ { \"key\": \"location\", \"operation\": \"eq\", \"value\": \"almaty\" }, { \"key\": \"location\", \"operation\": \"eq\", \"value\": \"karaganda\" } ] }, { \"joinBetween\": \"and\", \"expressions\": [ { \"key\": \"type\", \"operation\": \"eq\", \"value\": \"CAT\" } ] } ] }, \"sortParams\": [ { \"key\": \"name\", \"sort\":\"desc\" }, { \"key\": \"id\", \"sort\":\"asc\" } ] }"
                                    )
                            }
                    )
            )
            @Valid
            @RequestBody
            @NotNull(message = "Search parameters are required")
            SearchConfigDTO params
    ) {
        return ResponseEntity.ok(shelterService.findByParams(params));
    }

    @Operation(
            tags = "Операции с приютами",
            summary = "Добавление данных о приюте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Запрос успешно обработан, данные о приюте удалены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = ShelterListResponseDTO.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Приют с указанным id не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = BaseResponseDTO.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseDTO> deleteShelter(
            @Parameter(
                    description = "Идентификатор приюта",
                    required = true,
                    example = "6730a0374a524518f713fc75"
            )
            @PathVariable("id") String id
    ) {
        BaseResponseDTO response = shelterService.deleteShelterById(id);
        return ResponseEntity.ok(response);
    }
}
