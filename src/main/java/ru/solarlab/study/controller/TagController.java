package ru.solarlab.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.service.TagService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Контроллер тагов
 */
@Controller /* Компонент слоя управления */
@RequestMapping("/v1/tags") /* Задаёт адрес,
    по которому весь контроллер или его метод доступен на клиенте */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Tag( /* Описание компонента */
        name = "Контроллер тагов",
        description = "REST API для доступа к тагам")
@Validated /* Действует к параметрам, которые обозначены 
    аннотациями типа @min(), @max() и т.п. - Contract Validation */
public class TagController {

    // final: после присвоения объекта, нельзя изменить ссылку на данный объект,
    // а состояние объекта изменить можно
    private final TagService tagService;

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Создает таг",
            description = "Создает новый таг по данным из DTO")
    @PostMapping( /* Говорит, что этот метод должен быть вызван при запросе POST */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" })
    // Здесь "Long", а не "long", потому что
    // "Type argument cannot be of primitive type"
    public ResponseEntity<TagDto> createTag(
            @Parameter /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору 
                и только потом, после проверки, 
                его использует - Bean Validation */
            @RequestBody(required = false)
                    TagCreateDto request){

        // Не указываем тип <>, берётся тип результата
        return new ResponseEntity<>(
                tagService.create(request),
                HttpStatus.CREATED);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Обновляет таг",
            description = "Обновляет таг с указанным идентификатором по данным из DTO")
    @PutMapping( /* Говорит, что этот метод должен быть вызван при запросе PUT */
            value = "/{tagId}",
            produces = { "application/json" },
            consumes = { "application/json" })
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity updateTag(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор тага",
                    required = true,
                    example = "2")
            @Positive /* Допустимое значение > 0 */
            @PathVariable("tagId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long tagId,
            @Parameter /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору 
                и только потом, после проверки, 
                его использует - Bean Validation */
            @RequestBody(required = false)
                    TagUpdateDto request){

        return ResponseEntity.ok(
                tagService.update(
                        tagId,
                        request));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает таг по идентификатору")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/{tagId}",
            produces = { "application/json" })
    public ResponseEntity<TagDto> getTag(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор тага",
                    required = true,
                    example = "2")
            @Positive /* Допустимое значение > 0 */
            @PathVariable("tagId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long tagId){

        return ResponseEntity.ok(
                tagService.getById(tagId));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает коллекцию тагов",
            description = "Возвращает коллекцию тагов с пагинацией")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/",
            produces = { "application/json" })
    public ResponseEntity<List<TagDto>> getTags(
            @NotNull /* Показывает, что поле или параметр не может быть null */
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */        
                    description = "Количество тагов на странице",
                    required = true,
                    example = "1000")
            @Min(0) /* Минимальное допустимое значение */
            @Max(1000) /* Максимальное допустимое значение */
            @RequestParam( /* Извлекает параметр, переданный в запросе */
                value = "limit", required = true)
                    Integer limit) { // Integer, т.к. PageRequest требует Integer!

        return ResponseEntity.ok(
                tagService.getTags(limit));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Удаляет таг по идентификатору",
            description = "Таг из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping( /* Говорит, что этот метод должен быть вызван при запросе DELETE */
            value = "/{tagId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteTag(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор тага",
                    required = true,
                    example = "2")
            @Positive /* Допустимое значение > 0 */
            @PathVariable("tagId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long tagId){

        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();

    }

}

// https://bookflow.ru/rukovodstvo-po-rest-arhitekture/