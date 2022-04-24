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

@Controller /* Компонент слоя управления */
@RequestMapping("/v1/tags") /* Задаёт адрес, по которому весь контроллер или его метод доступен на клиенте */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Tag( /* Описание компонента */
        name = "Контроллер тагов",
        description = "REST API для доступа к тагам")
public class TagController {

    // final: после присвоения объекта, нельзя изменить ссылку на данный объект, а состояние объекта изменить можно
    private final TagService tagService;

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Создает таг",
            description = "Создает новый таг по данным из DTO")
    @PostMapping( /* Говорит, что этот метод должен быть вызван при запросе POST */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    public ResponseEntity<Integer> createTag(
            @Parameter /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestBody(required = false)
                    TagCreateDto dto){

        return new ResponseEntity<Integer>(
                tagService.create(dto),
                HttpStatus.CREATED);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Обновляет таг",
            description = "Обновляет таг с указанным идентификатором по данным из DTO")
    @PutMapping( /* Говорит, что этот метод должен быть вызван при запросе PUT */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateTag(
            @Parameter /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestBody(required = false)
                    TagUpdateDto dto) {

        return new ResponseEntity(HttpStatus.OK);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает таг по идентификатору")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/{tagId}",
            produces = { "application/json" }
    )
    public ResponseEntity<TagDto> getTag(
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                    description = "Идентификатор тага",
                    required = true)
            @PathVariable /* Извлекает параметр, переданный в адресе запроса */
                    Integer tagId) {

        return ResponseEntity.ok(
                tagService.getById(tagId));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает коллекцию тагов",
            description = "Возвращает коллекцию тагов с пагинацией")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/",
            produces = { "application/json" }
    )
    public ResponseEntity<List<TagDto>> getTags(
            @NotNull /* Показывает, что поле или параметр не может быть null */
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                description = "Количество тагов на странице",
                required = true)
            @Valid /* Отправляет объект параметра валидатору и только потом, после проверки, его использует */
            @RequestParam(value = "limit", required = true) /* Извлекает параметр, переданный в запросе */
                    Integer limit) {

        return ResponseEntity.ok(
                tagService.getTags(limit));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Удаляет таг по идентификатору",
            description = "Таг из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping( /* Говорит, что этот метод должен быть вызван при запросе DELETE */
            value = "/{tagId}"
    )
    public ResponseEntity<Void> deleteTag(
            @Parameter( /* The annotation may be used on a method parameter to define it as a parameter for the operation, and/or to define additional properties for the Parameter */
                    description = "Идентификатор тага",
                    required = true)
            @PathVariable("tagId") /* Извлекает параметр, переданный в адресе запроса */
                    Integer tagId) {

        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();

    }

}