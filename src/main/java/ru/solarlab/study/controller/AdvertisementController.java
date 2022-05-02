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
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.service.AdvertisementService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller /* Компонент слоя управления */
@RequestMapping("/v1/advertisements") /* Задаёт адрес,
    по которому весь контроллер или его метод доступен на клиенте */
@RequiredArgsConstructor /* Генерирует конструктор,
    принимающий значения для каждого final поля или 
    поля с аннотацией @NonNull. Аргументы конструктора 
    будут сгенерированы в том порядке, в котором поля 
    перечислены в классе. Для @NonNull полей конструктор 
    так же будет проверять, чтобы в него не передали значение null. */
@Tag( /* Описание компонента */
        name = "Контроллер объявлений",
        description = "REST API для доступа к объявлениям")
@Validated /* Действует к параметрам, которые обозначены 
    аннотациями типа @min(), @max() и т.п. - Contract Validation */
public class AdvertisementController {

    // final: после присвоения объекта, нельзя изменить ссылку на данный объект,
    // а состояние объекта изменить можно
    private final AdvertisementService advertisementService;

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Создает объявление",
            description = "Создает новое объявление по данным из DTO")
    @PostMapping( /* Говорит, что этот метод должен быть вызван при запросе POST */
            value = "/",
            produces = { "application/json" },
            consumes = { "application/json" }

    )
    // Здесь "Long", а не "long", потому что
    // "Type argument cannot be of primitive type"
    public ResponseEntity<Long> createAdvertisement(
            @Parameter /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору 
                и только потом, после проверки, 
                его использует - Bean Validation */
            @RequestBody(required = false)
                    AdvertisementCreateDto request){

        // Не указываем тип <>, берётся тип результата
        return new ResponseEntity<>(
                advertisementService.create(request),
                HttpStatus.CREATED);

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Обновляет объявление",
            description = "Обновляет объявление с указанным идентификатором по данным из DTO")
    @PutMapping( /* Говорит, что этот метод должен быть вызван при запросе PUT */
            value = "/{advertisementId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity updateAdvertisement(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор объявления",
                    required = true)
            @PositiveOrZero /* Допустимое значение >= 0 */
            @PathVariable("advertisementId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long advertisementId,
            @Parameter /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
            @Valid /* Отправляет объект параметра валидатору 
                и только потом, после проверки, 
                его использует - Bean Validation */
            @RequestBody(required = false)
                    AdvertisementUpdateDto request) throws Exception {

        return ResponseEntity.ok(
                advertisementService.update(
                        advertisementId,
                        request));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает объявление по идентификатору")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/{advertisementId}",
            produces = { "application/json" }
    )
    public ResponseEntity<AdvertisementDto> getAdvertisement(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор объявления",
                    required = true)
            @PositiveOrZero /* Допустимое значение >= 0 */
            @PathVariable("advertisementId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long advertisementId) throws Exception {

        return ResponseEntity.ok(
                advertisementService.getById(advertisementId));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Возвращает коллекцию объявлений",
            description = "Возвращает коллекцию объявлений с пагинацией")
    @GetMapping( /* Говорит, что этот метод должен быть вызван при запросе GET */
            value = "/",
            produces = { "application/json" }
    )
    public ResponseEntity<List<AdvertisementDto>> getAdvertisements(
            @NotNull /* Показывает, что поле или параметр не может быть null */
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                description = "Количество объявлений на странице",
                 required = true)
            @Min(0) /* Минимальное допустимое значение */
            @Max(20) /* Максимальное допустимое значение */
            @RequestParam( /* Извлекает параметр, переданный в запросе */
                value = "limit", required = true)
                    Integer limit) { // Integer, т.к. PageRequest требует Integer!

        return ResponseEntity.ok(
                advertisementService.getAdvertisements(limit));

    }

    @Operation( /* Описывает возможности методов контроллера */
            summary = "Удаляет объявление по идентификатору",
            description = "Объявление из базы не удаляется, меняется только статус на Удалено")
    @DeleteMapping( /* Говорит, что этот метод должен быть вызван при запросе DELETE */
            value = "/{advertisementId}"
    )
    public ResponseEntity<Void> deleteAdvertisement(
            @Parameter( /* The annotation may be used on
                a method parameter to define it as a parameter
                for the operation, and/or to define additional
                properties for the Parameter */
                    description = "Идентификатор объявления",
                    required = true)
            @PositiveOrZero /* Допустимое значение >= 0 */
            @PathVariable("advertisementId") /* Извлекает параметр,
                переданный в адресе запроса */
                    long advertisementId) throws Exception {

        advertisementService.deleteById(advertisementId);
        return ResponseEntity.noContent().build();

    }

}