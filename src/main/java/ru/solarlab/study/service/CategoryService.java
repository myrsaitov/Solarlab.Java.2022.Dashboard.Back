package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.mapper.CategoryMapper;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
public class CategoryService {

    /**
     * Объект маппера
     */
    private final CategoryMapper categoryMapper;

    /**
     * Создает новую категорию по данным из DTO
     * @param dto DTO для создания категории
     * @return Идентификатор нового тага
     */
    public Integer create(CategoryCreateDto dto) {

        return RandomUtils.nextInt();

    }

    /**
     * Обновляет категорию с указанным идентификатором по данным из DTO
     * @param dto DTO для обновления категории
     */
    public void update(
            CategoryUpdateDto dto) {

        if (!dto.id.equals(dto.getId()))
            throw new ValidationException("ID is not valid");

    }

    /**
     * Возвращает категорию по идентификатору
     * @param categoryId Идентификатор категории
     * @return Категория
     */
    public CategoryDto getById(
            Integer categoryId) {

        return CategoryDto.builder()
                .id(categoryId)
                .name("justDoIt")
                .createdAt(OffsetDateTime.now())
                .status(Status.NEW)
                .build();

    }

    /**
     * Возвращает коллекцию категорий с пагинацией
     * @param limit количество категорий на странице
     * @return Коллекция категорий
     */
    public List<CategoryDto> getCategories(
            Integer limit) {

        List<Category> list = getCategories();

        return list.stream()
                .limit(limit == null ? Integer.MAX_VALUE : limit)
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());

    }

    private List<Category> getCategories() {

        return List.of(
                Category.builder()
                        .id(RandomUtils.nextInt())
                        .name("justDoIt")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.NEW)
                        .build(),
                Category.builder()
                        .id(RandomUtils.nextInt())
                        .name("justDoIt2")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.IN_PROGRESS)
                        .build());

    }

    /**
     * Удаляет категорию по идентификатору
     * Категория из базы не удаляется, меняется только статус на "Удалено"
     * @param categoryId Идентификатор категории
     */
    public void deleteById(Integer categoryId) {



    }

}