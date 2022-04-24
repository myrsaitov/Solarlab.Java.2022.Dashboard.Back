package ru.solarlab.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.service.AdvertisementService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Tag(
        name = "Контроллер объявлений",
        description = "REST API для доступа к объявлениям")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Operation(
            summary = "Создает объявление",
            description = "Создает новое объявление по данным из DTO")
    @PostMapping(
            value = "/v1/advertisements",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    public ResponseEntity<Integer> createAdvertisement(
            @Parameter
            @Valid
            @RequestBody(required = false)
                    AdvertisementCreateDto dto){

        return new ResponseEntity<Integer>(
                advertisementService.create(dto),
                HttpStatus.CREATED);

    }

    @Operation(
            summary = "Обновляет объявление",
            description = "Обновляет объявление с указанным идентификатором по данным из DTO")
    @PutMapping(
            value = "/v1/advertisements/{advertisementId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateAdvertisement(
            @Parameter(
                    description = "Идентификатор объявления",
                    required = true)
            @PathVariable("advertisementId")
                    Integer advertisementId,
            @Parameter
            @Valid
            @RequestBody(required = false)
                    AdvertisementUpdateDto dto) {

        return new ResponseEntity(HttpStatus.OK);

    }

    @Operation(
            summary = "Возвращает объявление",
            description = "Возвращает объявление по идентефикатору")
    @GetMapping(
            value = "/v1/advertisements/{advertisementId}",
            produces = { "application/json" }
    )
    public ResponseEntity<AdvertisementDto> getAdvertisement(
            @Parameter(
                    description = "Идентификатор объявления",
                    required = true)
            @PathVariable
                    Integer advertisementId) {

        return ResponseEntity.ok(
                advertisementService.getById(advertisementId));

    }

    @Operation(
            summary = "Возвращает коллекцию объявлений",
            description = "Возвращает коллекцию объявлений с пагинацией")
    @GetMapping(
            value = "/v1/advertisements",
            produces = { "application/json" }
    )
    public ResponseEntity<List<AdvertisementDto>> getAdvertisements(
            @NotNull
            @Parameter(description = "Количество объявлений на странице", required = true)
            @Valid
            @RequestParam(value = "limit", required = true)
                    Integer limit) {

        return ResponseEntity.ok(
                advertisementService.getAdvertisements(limit));

    }

    @Operation(
            summary = "Удаляет объявление",
            description = "Объявление из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping(
            value = "/v1/advertisements/{advertisementId}"
    )
    public ResponseEntity<Void> deleteAdvertisement(
            @Parameter(
                    description = "Идентификатор объявления",
                    required=true)
            @PathVariable("advertisementId")
                    Integer advertisementId) {

        advertisementService.deleteById(advertisementId);
        return ResponseEntity.noContent().build();

    }

}