package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.dto.UserDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.exception.AdvertisementNotFoundException;
import ru.solarlab.study.exception.CannotEditOtherAdvertisementException;
import ru.solarlab.study.exception.CategoryNotFoundException;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.repository.AdvertisementRepository;
import ru.solarlab.study.repository.CategoryRepository;
import ru.solarlab.study.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис объявлений (бизнес-логика)
 */
@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* Генерирует конструктор,
    принимающий значения для каждого final поля или 
    поля с аннотацией @NonNull. 
    Аргументы конструктора будут сгенерированы в том порядке,
    в котором поля перечислены в классе. 
    Для @NonNull полей конструктор так же будет проверять,
    чтобы в него не передали значение null. */
public class AdvertisementService {

    /**
     * Объект маппера
     */
    private final AdvertisementMapper advertisementMapper;

    /**
     * Объекты репозиториев
     */
    private final AdvertisementRepository advertisementRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    /**
     * Максимальное количество на странице
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Создает новое объявление по данным из DTO
     * @param request DTO для создания объявления
     * @return Идентификатор созданного объявления
     */
    public AdvertisementDto create(AdvertisementCreateDto request) {

        try {

            // Проверяет существование категории с categoryId
            Category category = categoryRepository
                    .findById(request.getCategoryId())
                    .orElseThrow(
                            () -> new CategoryNotFoundException(
                                    request.getCategoryId()));

            // Создаёт сущность на основе DTO
            Advertisement advertisement = advertisementMapper
                    .advertisementCreateDtoToAdvertisement(
                            request,
                            getCurrentUser().getUsername());

            // Привязывает категорию к объявлению
            advertisement.setCategory(category);

            // Добавляет таги
            for (var tagId: request.getTagIds())  {

                // Возвращает таг по Id и если существует - добавляет
               tagRepository
                        .findById(tagId)
                        .ifPresent(
                                advertisement::addTag); // Вместо лямбды

            }

            // Сохраняет объявление в БД
            advertisementRepository.save(advertisement);

            // Привязывает объявление к категории
            category.addAdvertisement(advertisement);
            // Сохраняет категорию в БД
            categoryRepository.save(category);

            // Возвращает результат
            return advertisementMapper
                    .advertisementToAdvertisementDto(advertisement);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Обновляет объявление с указанным идентификатором по данным из DTO
     * @param advertisementId Идентификатор обновляемого объявления
     * @param request DTO для обновления объявления
     * @return Обновление прошло удачно
     */
    public AdvertisementDto update(
            long advertisementId,
            AdvertisementUpdateDto request){

        try {

            // Проверяет, может ли текущий авторизированный пользователь
            // обновлять объявление с таким advertisementId
            checkCurrentUserUpdatePermission(advertisementId);

            // Проверяет существование категории с categoryId
            Category newCategory = categoryRepository
                    .findById(request.getCategoryId())
                    .orElseThrow(
                            () -> new CategoryNotFoundException(
                                    request.getCategoryId()));

            // Достаёт из базы объявление с advertisementId
            Advertisement advertisement = advertisementRepository
                    .findByIdAndFetchCategory(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(
                                    advertisementId));

            // Обновляет поля объявления
            advertisementMapper.advertisementUpdateDtoToAdvertisement(
                    advertisement,
                    request,
                    getCurrentUser().getUsername());

            // Удаляет прикрепленные таги:

            // Получает список индексов тагов заранее,
            // т.к. нельзя в цикле изменять коллекцию
            var tagIds = advertisement.getTags().stream()
                    .map(Tag::getId).collect(Collectors.toList());

            // Открепляет таги
            tagIds.forEach(
                    advertisement::removeTag); // Вместо лямбды

            // Добавляет таги
            for (var tagId: request.getTagIds())  {

                // Возвращает таг по Id и если существует - добавляет
                tagRepository
                        .findById(tagId)
                        .ifPresent(
                                advertisement::addTag); // Вместо лямбды

            }

            // Сохраняет в базе
            advertisementRepository.save(advertisement);

            // Старая категория
            var oldCategory = advertisement.getCategory();

            // Если категории не совпадают
            if(newCategory.getId() != oldCategory.getId()){

                // Привязывает новую категорию к объявлению
                advertisement.setCategory(newCategory);
                // Сохраняет в базе
                advertisementRepository.save(advertisement);

                // Удаляет ссылку на объявление у старой категории
                oldCategory.removeAdvertisement(advertisement);
                // Сохраняет в базе
                categoryRepository.save(oldCategory);

                // Привязываем объявление к новой категории
                newCategory.addAdvertisement(advertisement);
                // Сохраняет в базе
                categoryRepository.save(newCategory);

            }

            // Возвращает результат
            return advertisementMapper
                    .advertisementToAdvertisementDto(
                            advertisement);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает объявление по идентификатору
     * @param advertisementId Идентификатор объявления
     * @return Объявление
     */
    public AdvertisementDto getById(
            long advertisementId){

        try {

            Advertisement advertisement = advertisementRepository
                    .findByIdAndFetchCategory(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));

            return advertisementMapper
                    .advertisementToAdvertisementDto(advertisement);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией
     * @param page Номер страницы
     * @param size Количество объявлений на странице
     * @param direction Порядок сортировки (возрастающий, убывающий)
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisements(
            Integer page,
            Integer size,
            Sort.Direction direction) {

        try {

            // С любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                return advertisementRepository
                        .findAll(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()))
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());
            }
            // Только с активным статусом
            else {

                return advertisementRepository
                        .findActive(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()))
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией и фильтром по категории
     * @param page Номер страницы
     * @param size Количество объявлений на странице
     * @param direction Порядок сортировки (возрастающий, убывающий)
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisementsByCategory(
            Integer page,
            Integer size,
            Sort.Direction direction,
            Long categoryId) {
        try {

            // С любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {
                return advertisementRepository
                        .findAllByCategory(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                categoryId)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());
            }
            // Только с активным статусом
            else {

                return advertisementRepository
                        .findActiveByCategory(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                categoryId)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией и фильтром по тагу
     * @param page Номер страницы
     * @param size Количество объявлений на странице
     * @param direction Порядок сортировки (возрастающий, убывающий)
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisementsByTag(
            Integer page,
            Integer size,
            Sort.Direction direction,
            Long tagId) {
        try {

            // С любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                return advertisementRepository
                        .findAllByTag(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                tagId)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());
            }
            // Только с активным статусом
            else {

                return advertisementRepository
                        .findActiveByTag(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                tagId)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией и фильтром по тагу
     * @param page Номер страницы
     * @param size Количество объявлений на странице
     * @param direction Порядок сортировки (возрастающий, убывающий)
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisementsByOwner(
            Integer page,
            Integer size,
            Sort.Direction direction,
            String owner) {

        try {

            // С любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                return advertisementRepository
                        .findAllByOwner(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                owner)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());

            }
            // Только с активным статусом
            else {

                return advertisementRepository
                        .findActiveByOwner(
                                PageRequest.of(
                                        page == null ? 0 : page,
                                        size == null ? DEFAULT_PAGE_SIZE : size,
                                        Sort.unsorted()),
                                owner)
                        .stream()
                        .map(advertisementMapper::advertisementToAdvertisementDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Удаляет объявление по идентификатору
     * @param advertisementId Идентификатор объявления
     */
    public void deleteById(long advertisementId){

        try {

            // Получает объявление из базы
            Advertisement advertisement = advertisementRepository
                    .findById(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));

            // Удаляет прикрепленные таги:

            // Получает список индексов тагов заранее,
            // т.к. нельзя в цикле изменять коллекцию
            var tagIds = advertisement.getTags().stream()
                    .map(Tag::getId).collect(Collectors.toList());

            // Открепляет таги
            tagIds.forEach(
                    advertisement::removeTag); // Вместо лямбды

            // Удаляет из базы на совсем
            advertisementRepository.deleteById(advertisementId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Проверяет, разрешено ли текущему авторизированному
     * пользователю редактировать объявление
     * @param advertisementId Идентификатор объявления
     */
    private void checkCurrentUserUpdatePermission(
            Long advertisementId) {

        UserDto currentUser = getCurrentUser();

        Optional<Advertisement> optionalAdvertisement = advertisementRepository
                .findById(advertisementId);
        if (optionalAdvertisement.isPresent() &&
            !optionalAdvertisement
                .get()
                .getOwner()
                .equals(currentUser.getUsername()) &&
            !currentUser.getRole().equals("ADMIN")) {

            throw new CannotEditOtherAdvertisementException();

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


// Сортировка по столбцам таблицы: appleName - толбец
// https://wesome.org/pagerequest
// https://www.baeldung.com/spring-data-jpa-pagination-sorting
//Pageable page = PageRequest.of(0, 5, Sort.Direction.DESC, new String[]{"appleName"});
//Page<Apple> apples = appleRepository.findAll(page);
//Sort.by("price").descending().and(Sort.by("name"))