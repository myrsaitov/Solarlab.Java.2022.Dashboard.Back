package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.mapper.CategoryMapper;
import ru.solarlab.study.mapper.CategoryMapper;
import ru.solarlab.study.repository.CategoryRepository;

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
     * Объект репозитория
     */
    private final CategoryRepository categoryRepository;

    /**
     * Максимальное количество на странице
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Создает новую категорию по данным из DTO
     * @param request DTO для создания категории
     * @return Идентификатор созданного категории
     */
    public Integer create(CategoryCreateDto request) {

        Category category = categoryMapper.toCategory(request);
        category.createdAt = OffsetDateTime.now();
        categoryRepository.save(category);
        return category.id;

    }

    /**
     * Обновляет категорию с указанным идентификатором по данным из DTO
     * @param request DTO для обновления категории
     * @return Обновление прошло удачно
     */
    public boolean update(
            CategoryUpdateDto request) {

        /**
         * Достает из базы по Id
         */
        Category category = categoryRepository
                .findById(request.id)
                .orElse(null);

        /**
         * Если в базе есть с таким Id
         */
        if(category != null) {

            category.updatedAt = OffsetDateTime.now();
            category.name = request.name;
            category.status = request.status;

            categoryRepository.save(category);

            return true;
        }
        /**
         * Если в базе нет с таким Id
         */
        else {

            return false;

        }

    }

    /**
     * Возвращает категорию по идентификатору
     * @param categoryId Идентификатор категории
     * @return Категория
     */
    public CategoryDto getById(
            Integer categoryId) {

        return categoryMapper
                .categoryToCategoryDto(
                        categoryRepository
                                .findById(categoryId)
                                .orElse(null));

    }

    /**
     * Возвращает коллекцию категорий с пагинацией
     * @param limit количество категорий на странице
     * @return Коллекция категорий
     */
    public List<CategoryDto> getCategories(
            Integer limit) {

        return categoryRepository
                .findAll(
                        PageRequest.of(
                                0,
                                limit == null ? DEFAULT_PAGE_SIZE : limit))
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());

    }

    /**
     * Удаляет категорию по идентификатору
     * Категория из базы не удаляется, меняется только статус на "Удалено"
     * @param categoryId Идентификатор категории
     */
    public void deleteById(Integer categoryId) {

        categoryRepository.deleteById(categoryId);

    }

}