package ru.solarlab.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller /* Компонент слоя управления */
@RequestMapping("/v1/categories") /* Задаёт адрес, по которому весь контроллер или его метод доступен на клиенте */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Tag( /* Описание компонента */
        name = "Контроллер категорий",
        description = "REST API для доступа к категориям")
@Validated /* Действует как @Valid, но только по отношению не к одному, а к группе параметров (всех параметров всех методов класса) */
public class CategoryController {

    // final: после присвоения объекта, нельзя изменить ссылку на данный объект, а состояние объекта изменить можно
    private final CategoryService categoryService;

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Создает категорию",
            description = "Создает новую категорию по данным из DTO")
    @PostMapping( /* Говорит, что этот метод должен быть вызван при запросе POST */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    public ResponseEntity<Integer> createCategory(
            @Parameter /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestBody(required = false)
                    CategoryCreateDto dto){

        return new ResponseEntity<Integer>(
                categoryService.create(dto),
                HttpStatus.CREATED);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Обновляет категорию",
            description = "Обновляет категорию с указанным идентификатором по данным из DTO")
    @PutMapping( /* Говорит, что этот метод должен быть вызван при запросе PUT */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateCategory(
            @Parameter /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestBody(required = false)
                    CategoryUpdateDto dto) {

        return new ResponseEntity(HttpStatus.OK);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает категорию по идентификатору")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/{categoryId}",
            produces = { "application/json" }
    )
    public ResponseEntity<CategoryDto> getCategory(
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                    description = "Идентификатор категории",
                    required = true)
            @PathVariable /* Извлекает параметр, переданный в адресе запроса */
                    Integer categoryId) {

        return ResponseEntity.ok(
                categoryService.getById(categoryId));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает коллекцию категорий",
            description = "Возвращает коллекцию категорий с пагинацией")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/",
            produces = { "application/json" }
    )
    public ResponseEntity<List<CategoryDto>> getCategories(
            @NotNull /* Показывает, что поле или параметр не может быть null */
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                description = "Количество категорий на странице",
                required = true)
            @Min(0) /* Минимальное допустимое значение */
            @Max(20) /* Максимальное допустимое значение */
            //@Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestParam(value = "limit", required = true) /* Извлекает параметр, переданный в запросе */
                    Integer limit) {

        return ResponseEntity.ok(
                categoryService.getCategories(limit));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Удаляет категорию по идентификатору",
            description = "Категория из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping( /* Говорит, что этот метод должен быть вызван при запросе DELETE */
            value = "/{categoryId}"
    )
    public ResponseEntity<Void> deleteCategory(
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                    description = "Идентификатор категории",
                    required = true)
            @PathVariable("categoryId") /* Извлекает параметр, переданный в адресе запроса */
                    Integer categoryId) {

        categoryService.deleteById(categoryId);
        return ResponseEntity.noContent().build();

    }

}