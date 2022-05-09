package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.dto.UserDto;
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
    public CategoryDto create(CategoryCreateDto request) {

        try {

            // TODO Проверять на наличие повторяющейся категории

            // Создаёт сущность на основе DTO
            Category category = categoryMapper
                    .categoryCreateDtoToCategory(request);

            // Достаёт из базы родительскую категорию с parentCategoryId
            categoryRepository
                    .findById(request.getParentCategoryId())
                    .ifPresent(
                            parentcat ->
                                    parentcat.addSubCategory(category));

            // Сохраняет категорию в БД
            categoryRepository.save(category);

            // Возвращает результат
            return categoryMapper
                    .categoryToCategoryDto(category);

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

            // Достаёт из базы родительскую категорию с parentCategoryId
            categoryRepository
                    .findById(request.getParentCategoryId())
                    .ifPresent(
                            parentcat ->
                                    parentcat.addSubCategory(category));

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

            if (getCurrentUser().getRole().equals("ADMIN")) {

                // С любым статусом
                Category category = categoryRepository
                        .findById(categoryId)
                        .orElseThrow(
                                () -> new CategoryNotFoundException(categoryId));
                return categoryMapper.categoryToCategoryDto(category);

            }
            else {

                // Только с активным статусом
                Category category = categoryRepository
                        .findActiveById(categoryId)
                        .orElseThrow(
                                () -> new CategoryNotFoundException(categoryId));
                return categoryMapper.categoryToCategoryDto(category);

            }

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

        try {

            // Возвращает таги с любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                return categoryRepository
                        .findAll(
                                PageRequest.of(
                                        0,
                                        limit == null ? DEFAULT_PAGE_SIZE : limit))
                        .stream()
                        .map(categoryMapper::categoryToCategoryDto)
                        .collect(Collectors.toList());

            }
            // Возвращает таги только с активным статусом
            else {

                return categoryRepository
                        .findActive(
                                PageRequest.of(
                                        0,
                                        limit == null ? DEFAULT_PAGE_SIZE : limit))
                        .stream()
                        .map(categoryMapper::categoryToCategoryDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }
    }

    /**
     * Удаляет категорию по идентификатору из БД
     * При удалении категории из БД удаляются связанные объявления!
     * @param categoryId Идентификатор категории
     */
    public void deleteById(long categoryId){

        try {

            // Достаёт из базы категорию с categoryId
            Category category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(categoryId));

            // Достаёт из базы родительскую категорию и отвязывает от нее подкатегорию
            categoryRepository
                    .findById(category.getParentCategory().getId())
                    .ifPresent(
                            parentcat ->
                                    parentcat.removeSubCategory(category));

            // Удаляет категорию из БД
            categoryRepository.deleteById(categoryId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает текущего авторизированного пользователя
     * @return
     */
    private UserDto getCurrentUser() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext
                .getAuthentication()
                .getPrincipal()
                .toString();

        String role = securityContext
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        return new UserDto(username, role);

    }

}