package ru.solarlab.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Tag(
        name = "Контроллер категорий",
        description = "REST API для доступа к категориям")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Создает категорию",
            description = "Создает новую категорию по данным из DTO")
    @PostMapping(
            value = "/v1/categories",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    public ResponseEntity<Integer> createCategory(
            @Parameter
            @Valid
            @RequestBody(required = false)
                    CategoryCreateDto dto){

        return new ResponseEntity<Integer>(
                categoryService.create(dto),
                HttpStatus.CREATED);

    }

    @Operation(
            summary = "Обновляет категорию",
            description = "Обновляет категорию с указанным идентификатором по данным из DTO")
    @PutMapping(
            value = "/v1/categories/{categoryId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateCategory(
            @Parameter(
                    description = "Идентификатор категории",
                    required = true)
            @PathVariable("categoryId")
                    Integer categoryId,
            @Parameter
            @Valid
            @RequestBody(required = false)
                    CategoryUpdateDto dto) {

        return new ResponseEntity(HttpStatus.OK);

    }

    @Operation(
            summary = "Возвращает категорию",
            description = "Возвращает категорию по идентефикатору")
    @GetMapping(
            value = "/v1/categories/{categoryId}",
            produces = { "application/json" }
    )
    public ResponseEntity<CategoryDto> getCategory(
            @Parameter(
                    description = "Идентификатор категории",
                    required = true)
            @PathVariable
                    Integer categoryId) {

        return ResponseEntity.ok(
                categoryService.getById(categoryId));

    }

    @Operation(
            summary = "Возвращает коллекцию категорий",
            description = "Возвращает коллекцию категорий с пагинацией")
    @GetMapping(
            value = "/v1/categories",
            produces = { "application/json" }
    )
    public ResponseEntity<List<CategoryDto>> getCategories(
            @NotNull
            @Parameter(description = "Количество категорий на странице", required = true)
            @Valid
            @RequestParam(value = "limit", required = true)
                    Integer limit) {

        return ResponseEntity.ok(
                categoryService.getCategories(limit));

    }

    @Operation(
            summary = "Удаляет категорию",
            description = "категория из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping(
            value = "/v1/categories/{categoryId}"
    )
    public ResponseEntity<Void> deleteCategory(
            @Parameter(
                    description = "Идентификатор категории",
                    required=true)
            @PathVariable("categoryId")
                    Integer categoryId) {

        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();

    }

}