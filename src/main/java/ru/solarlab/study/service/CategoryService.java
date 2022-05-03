package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.exception.CategoryNotFoundException;
import ru.solarlab.study.mapper.CategoryMapper;
import ru.solarlab.study.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* Генерирует конструктор,
    принимающий значения для каждого final поля или 
    поля с аннотацией @NonNull. 
    Аргументы конструктора будут сгенерированы в том порядке,
    в котором поля перечислены в классе. 
    Для @NonNull полей конструктор так же будет проверять,
    чтобы в него не передали значение null. */
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
    public Long create(CategoryCreateDto request) {

        try {

            // TODO Проверять на наличие повторяющейся категории

            // Создаёт сущность на основе DTO
            Category category = categoryMapper
                    .categoryCreateDtoToCategory(request);

            // Сохраняет категорию в БД
            categoryRepository.save(category);

            // Возвращает результат
            return category.id;

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Обновляет категорию с указанным идентификатором по данным из DTO
     * @param categoryId Идентификатор обновляемой категории
     * @param request DTO для обновления категории
     * @return Обновление прошло удачно
     */
    public CategoryDto update(
            long categoryId,
            CategoryUpdateDto request){

        try {

            // Достаёт из базы категорию с categoryId
            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(categoryId));

            // Обновляет поля категории
            categoryMapper.categoryUpdateDtoToCategory(
                    category,
                    request);

            // Сохраняет в базе
            categoryRepository.save(category);

            // Возвращает результат
            return categoryMapper.categoryToCategoryDto(category);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает категорию по идентификатору
     * @param categoryId Идентификатор категории
     * @return Категория
     */
    public CategoryDto getById(
            long categoryId){

        try {

            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(categoryId));
            return categoryMapper.categoryToCategoryDto(category);

        }
        catch (Exception ex) {

            throw ex;

        }

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
    public void deleteById(long categoryId){

        try {

            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(categoryId));
            categoryRepository.deleteById(categoryId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

}