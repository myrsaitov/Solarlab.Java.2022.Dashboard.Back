package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.exception.AdvertisementNotFoundException;
import ru.solarlab.study.exception.CategoryNotFoundException;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.repository.AdvertisementRepository;
import ru.solarlab.study.repository.CategoryRepository;
import ru.solarlab.study.repository.TagRepository;

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
    public Long create(AdvertisementCreateDto request) {

        try {

            // Проверяет существование категории с categoryId
            Category category = categoryRepository
                    .findById(request.categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(request.categoryId));

            // Создаёт сущность на основе DTO
            Advertisement advertisement = advertisementMapper
                    .advertisementCreateDtoToAdvertisement(request);

            // Привязывает категорию к объявлению
            advertisement.category = category;

            // Добавляет таги
            for (var tagId: request.tagId)  {

                // Возвращает таг по Id и если существует - добавляет
               tagRepository
                        .findById(tagId)
                        .ifPresent(
                                tag ->
                                advertisement.addTag(tag));

            }

            // Сохраняет объявление в БД
            advertisementRepository.save(advertisement);

            // Привязывает объявление к категории
            category.addAdvertisement(advertisement);
            // Сохраняет категорию в БД
            categoryRepository.save(category);

            // Возвращает результат
            return advertisement.id;

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

            // Проверяет существование категории с categoryId
            Category newCategory = categoryRepository
                    .findById(request.categoryId)
                    .orElseThrow(
                            () -> new CategoryNotFoundException(request.categoryId));

            // Достаёт из базы объявление с advertisementId
            Advertisement advertisement = advertisementRepository
                    .findByIdAndFetchCategory(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));

            // Обновляет поля объявления
            advertisementMapper.advertisementUpdateDtoToAdvertisement(
                    advertisement,
                    request);

            // Удаляет старые таги

            // Получает список индексов тагов заранее,
            // т.к. нельзя в цикле изменять коллекцию
            var tagIds = advertisement.getTags().stream()
                    .map(Tag::getId).collect(Collectors.toList());

            tagIds.forEach(
                    tagId -> advertisement.removeTag(tagId));

            // Добавляет таги
            for (var tagId: request.tagId)  {

                // Возвращает таг по Id и если существует - добавляет
                tagRepository
                        .findById(tagId)
                        .ifPresent(
                                tag ->
                                        advertisement.addTag(tag));

            }

            // Сохраняет в базе
            advertisementRepository.save(advertisement);

            // Старая категория
            var oldCategory = advertisement.category;

            // Если категории не совпадают
            if(newCategory.id != oldCategory.id){

                // Привязывает новую категорию к объявлению
                advertisement.category = newCategory;
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
            return advertisementMapper.advertisementToAdvertisementDto(advertisement);

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

            AdvertisementDto result = advertisementMapper
                    .advertisementToAdvertisementDto(advertisement);

            return result;

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией
     * @param limit количество объявлений на странице
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisements(
            Integer limit) {

        return advertisementRepository
                .findAll(
                        PageRequest.of(
                                0,
                                limit == null ? DEFAULT_PAGE_SIZE : limit))
                .stream()
                .map(advertisementMapper::advertisementToAdvertisementDto)
                .collect(Collectors.toList());

    }

    /**
     * Удаляет объявление по идентификатору
     * Объявление из базы не удаляется, меняется только статус на "Удалено"
     * @param advertisementId Идентификатор объявления
     */
    public void deleteById(long advertisementId){

        try {

            Advertisement advertisement = advertisementRepository
                    .findById(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));
            advertisementRepository.deleteById(advertisementId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

}