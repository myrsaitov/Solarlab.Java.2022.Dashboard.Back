package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.*;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Data
public class CategoryService {

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
     * Возвращает категорию по идентефикатору
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

        List<CategoryDto> list = getCategories();

        return list.stream()
                .limit(limit == null ? Integer.MAX_VALUE : limit)
                .collect(Collectors.toList());

    }

    private List<CategoryDto> getCategories() {

        return List.of(
                CategoryDto.builder()
                        .id(RandomUtils.nextInt())
                        .name("justDoIt")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.NEW)
                        .build(),
                CategoryDto.builder()
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