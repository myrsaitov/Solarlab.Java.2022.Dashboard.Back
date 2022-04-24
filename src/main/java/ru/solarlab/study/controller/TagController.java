package ru.solarlab.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Tag(
        name = "Контроллер тагов",
        description = "REST API для доступа к тагам")
public class TagController {

    private final TagService tagService;

    @Operation(
            summary = "Создает таг",
            description = "Создает новый таг по данным из DTO")
    @PostMapping(
            value = "/v1/tags",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    public ResponseEntity<Integer> createTag(
            @Parameter
            @Valid
            @RequestBody(required = false)
                    TagCreateDto dto){

        return new ResponseEntity<Integer>(
                tagService.create(dto),
                HttpStatus.CREATED);

    }

    @Operation(
            summary = "Обновляет таг",
            description = "Обновляет таг с указанным идентификатором по данным из DTO")
    @PutMapping(
            value = "/v1/tags",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateTag(
            @Parameter
            @Valid
            @RequestBody(required = false)
                    TagUpdateDto dto) {

        return new ResponseEntity(HttpStatus.OK);

    }

    @Operation(
            summary = "Возвращает таг",
            description = "Возвращает таг по идентефикатору")
    @GetMapping(
            value = "/v1/tags/{tagId}",
            produces = { "application/json" }
    )
    public ResponseEntity<TagDto> getTag(
            @Parameter(
                    description = "Идентификатор тага",
                    required = true)
            @PathVariable
                    Integer tagId) {

        return ResponseEntity.ok(
                tagService.getById(tagId));

    }

    @Operation(
            summary = "Возвращает коллекцию тагов",
            description = "Возвращает коллекцию тагов с пагинацией")
    @GetMapping(
            value = "/v1/tags",
            produces = { "application/json" }
    )
    public ResponseEntity<List<TagDto>> getTags(
            @NotNull
            @Parameter(description = "Количество тагов на странице", required = true)
            @Valid
            @RequestParam(value = "limit", required = true)
                    Integer limit) {

        return ResponseEntity.ok(
                tagService.getTags(limit));

    }

    @Operation(
            summary = "Удаляет таг",
            description = "Таг из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping(
            value = "/v1/tags/{tagId}"
    )
    public ResponseEntity<Void> deleteTag(
            @Parameter(
                    description = "Идентификатор тага",
                    required=true)
            @PathVariable("tagId")
                    Integer tagId) {

        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();

    }

}