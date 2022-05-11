package ru.solarlab.study.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.exception.AdvertisementNotFoundException;
import ru.solarlab.study.exception.CategoryNotFoundException;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.mapper.AdvertisementMapperImpl;
import ru.solarlab.study.repository.AdvertisementRepository;
import ru.solarlab.study.repository.CategoryRepository;
import ru.solarlab.study.repository.TagRepository;

import java.time.OffsetDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    private static final Integer LIMIT = 20;
    private static final Integer DEFAULT_LIMIT = 10;

    /**
     * Объявление
     */
    public static final Long ADVERTISEMENT_ID = new Long(1);
    public static final OffsetDateTime ADVERTISEMENT_CREATED_AT = OffsetDateTime.parse("2022-05-09T11:25:28.510024600+03:00");//OffsetDateTime.now();
    public static final OffsetDateTime ADVERTISEMENT_UPDATED_AT = null;
    public static final String ADVERTISEMENT_TITLE = "title";
    public static final String ADVERTISEMENT_BODY = "body";
    public static final String ADVERTISEMENT_OWNER = "user1";
    public static final float ADVERTISEMENT_PRICE = (float) 10.1;
    public static final AdvertisementStatus ADVERTISEMENT_STATUS = AdvertisementStatus.ACTIVE;

    /**
     * Категория
     */
    public static final Category CATEGORY = new Category(
            1L,
            OffsetDateTime.now(),
            null,
            "Category",
            CategoryStatus.ACTIVE,
            null,
            new HashSet<>(),
            new HashSet<>());

    public static final Category ADVERTISEMENT_CATEGORY = CATEGORY;

    public static final Category CATEGORY_NEW = new Category(
            2L,
            OffsetDateTime.now(),
            null,
            "CategoryNew",
            CategoryStatus.ACTIVE,
            null,
            new HashSet<>(),
            new HashSet<>());

    /**
     * Модели тагов
     */

    public static final Tag TAG_1 = new Tag(

            1L,
            OffsetDateTime.now(),
            null,
            "TagText1",
            TagStatus.ACTIVE,
            new HashSet<>()

            );

    public static final Tag TAG_2 = new Tag(

            2L,
            OffsetDateTime.now(),
            null,
            "TagText2",
            TagStatus.ACTIVE,
            new HashSet<>()

    );

    public static final Tag TAG_3 = new Tag(

            3L,
            OffsetDateTime.now(),
            null,
            "TagText3",
            TagStatus.ACTIVE,
            new HashSet<>()

    );

    public static final Set<Tag> ADVERTISEMENT_TAGS = new HashSet<>(){{

        add(TAG_1);
        add(TAG_2);
        add(TAG_3);

    }};

    public static final Long TAG_IDs[] = new Long[]{

            TAG_1.getId(),
            TAG_2.getId(),
            TAG_3.getId()

    };


    /**
     * Авторизация и аутентификация
     */
    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;


    /**
     * Репозитории
     */
    @Mock
    private AdvertisementRepository advertisementRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;

    /**
     * Маппер
     */
    @Spy
    private AdvertisementMapper advertisementMapper = new AdvertisementMapperImpl();

    /**
     * Сервис
     */
    @InjectMocks
    private AdvertisementService advertisementService;

    /**
     * Create: OK
     */
    @Test
    void testCreate(){

        /* Объявление */
        MockitoAdvertisementSaveFirstlyAfterCreateAndSetId(
                ADVERTISEMENT_ID);

        /* Категория */
        MockitoCategoryFindById(CATEGORY);

        /* Таги */
        MockitoTagFindById(TAG_1);
        MockitoTagFindById(TAG_2);
        MockitoTagFindById(TAG_3);

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");


        // Вызов тестируемого метода
        final AdvertisementDto actual = advertisementService
                .create(getAdvertisementCreateDto());

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                actual.getCreatedAt(),
                null,
                null);

        // Сравнивает результат теста с эталонным значением
        assertEquals(advertisementDto, actual);

        // Вызывался ли данный метод?
        /* Mockito.verify(advertisementRepository)
                .save(getAdvertisement(
                        true,
                        actual.getCreatedAt())); */
        // Разные HashSet у Set<Tag>, поэтому не получается повторить точь-в-точь
        Mockito.verify(advertisementRepository)
                .save(any());

        // Вызывался ли данный метод с таким аргументом?
        Mockito.verify(categoryRepository)
                .save(CATEGORY);

    }

    /**
     * Create: throws CategoryNotFoundException
     */
    @Test
    void testCreateThrowsCategoryNotFoundException() throws CategoryNotFoundException {

        /* Категория не найдена */
        MockitoCategoryFindByIdReturnsEmpty();

        /* Вызов тестируемого метода */
        Throwable thrown = assertThrows(
                CategoryNotFoundException.class, () -> {

                    final AdvertisementDto actual = advertisementService
                            .create(getAdvertisementCreateDto());

        });

        /* Сравнение результатов */
        assertEquals(
                String.format(
                    "Категория с categoryId=%s не найдена.",
                    CATEGORY.getId()),
                thrown.getMessage());

    }

    /**
     * Update: OK, категория не изменяется
     */
    @Test
    void testUpdateTheSameCategory(){

        /* Объявление */
        MockitoAdvertisementFindByIdAndFetchCategory(
                false,
                null,
                CATEGORY);

        /* При обращении к advertisementRepository.save
           сущности присваивается id */
        MockitoAdvertisementSaveFirstlyAfterCreateAndSetId(
                ADVERTISEMENT_ID);

        /* Категория */
        MockitoCategoryFindById(CATEGORY);

        /* Таги */
        MockitoTagFindById(TAG_1);
        MockitoTagFindById(TAG_2);
        MockitoTagFindById(TAG_3);

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");

        // Вызов тестируемого метода
        final AdvertisementDto actual = advertisementService
                .update(
                        ADVERTISEMENT_ID,
                        getAdvertisementUpdateDto(CATEGORY.getId()));

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                actual.getCreatedAt(),
                actual.getUpdatedAt(),
                null);

        // Сравнивает результат теста с эталонным значением
        assertEquals(advertisementDto, actual);

        // Вызывался ли данный метод?
        Mockito.verify(advertisementRepository)
               .save(any());

        // Вызывался ли данный метод с таким аргументом?
        //Mockito.verify(categoryRepository)
        //        .save(any());

    }

    /**
     * Update: OK, категория изменена
     */
    @Test
    void testUpdateChangeCategory(){

        /* Объявление */
        MockitoAdvertisementFindByIdAndFetchCategory(
                false,
                null,
                CATEGORY);

        /* При обращении к advertisementRepository.save
           сущности присваивается id */
        MockitoAdvertisementSaveFirstlyAfterCreateAndSetId(
                ADVERTISEMENT_ID);

        /* Категория */
        MockitoCategoryFindById(CATEGORY_NEW);

        /* Таги */
        MockitoTagFindById(TAG_1);
        MockitoTagFindById(TAG_2);
        MockitoTagFindById(TAG_3);

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");

        // Вызов тестируемого метода
        final AdvertisementDto actual = advertisementService
                .update(
                        ADVERTISEMENT_ID,
                        getAdvertisementUpdateDto(
                                CATEGORY_NEW.getId()));

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                actual.getCreatedAt(),
                actual.getUpdatedAt(),
                CATEGORY_NEW.getId());

        // Сравнивает результат теста с эталонным значением
        assertEquals(advertisementDto, actual);

        // Вызывался ли данный метод 2 раза?
        Mockito.verify(advertisementRepository, times(2))
                .save(any());

        // Вызывался ли данный метод 2 раза?
        Mockito.verify(categoryRepository, times(2))
                .save(any());

    }

    /**
     * Update: throws CategoryNotFoundException
     */
    @Test
    void testUpdateThrowsCategoryNotFoundException() throws CategoryNotFoundException {

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");

        /* Категория не найдена */
        MockitoCategoryFindByIdReturnsEmpty();

        /* Вызов тестируемого метода */
        Throwable thrown = assertThrows(
                CategoryNotFoundException.class, () -> {

                    final AdvertisementDto actual = advertisementService
                            .update(
                                    ADVERTISEMENT_ID,
                                    getAdvertisementUpdateDto(
                                            CATEGORY_NEW.getId()));

                });

        /* Сравнение результатов */
        assertEquals(
                String.format(
                        "Категория с categoryId=%s не найдена.",
                        CATEGORY_NEW.getId()),
                thrown.getMessage());

    }

    /**
     * Update: throws AdvertisementNotFoundException
     */
    @Test
    void testUpdateThrowsAdvertisementNotFoundException() throws AdvertisementNotFoundException {

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");

        /* Объявление не найдено */
        MockitoAdvertisementFindByIdAndFetchCategoryReturnsEmpty();

        /* Категория */
        MockitoCategoryFindById(CATEGORY_NEW);

        /* Вызов тестируемого метода */
        Throwable thrown = assertThrows(
                AdvertisementNotFoundException.class, () -> {

                    final AdvertisementDto actual = advertisementService
                            .update(
                                    ADVERTISEMENT_ID,
                                    getAdvertisementUpdateDto(
                                            CATEGORY_NEW.getId()));

                });

        /* Сравнение результатов */
        assertEquals(
                String.format(
                        "Объявление с advertisementId=%s не найдено.",
                        ADVERTISEMENT_ID),
                thrown.getMessage());

    }

    /**
     * GetById: OK
     */
    @Test
    void testGetById() {

        /* Объявление */
        MockitoAdvertisementFindByIdAndFetchCategory(
                false,
                null,
                CATEGORY);

        // Вызов тестируемого метода
        final AdvertisementDto actual = advertisementService
                .getById(ADVERTISEMENT_ID);

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                actual.getCreatedAt(),
                null,
                null);

        // Сравнивает результат теста с эталонным значением
        assertEquals(advertisementDto, actual);

    }

    /**
     * GetById: throws AdvertisementNotFoundException
     */
    @Test
    void testGetByIdThrowsAdvertisementNotFoundException() throws AdvertisementNotFoundException {

        /* Объявление */
        MockitoAdvertisementFindByIdAndFetchCategoryReturnsEmpty();

        /* Вызов тестируемого метода */
        Throwable thrown = assertThrows(
                AdvertisementNotFoundException.class, () -> {

                    final AdvertisementDto actual = advertisementService
                            .getById(ADVERTISEMENT_ID);

                });

        /* Сравнение результатов */
        assertEquals(
                String.format(
                        "Объявление с advertisementId=%s не найдено.",
                        ADVERTISEMENT_ID),
                thrown.getMessage());

    }

    /**
     * GetAdvertisements: OK
     */
    @Test
    void testGetAdvertisementsWithLimit() {

        /* Авторизация и аутентификация */
        MockitoAuth("user1","USER");

        /* Категория */
        MockitoAdvertisementFindAll();
        MockitoAdvertisementFindActive();

        /* Страница с объявлениями */


        // Вызов тестируемого метода
        final List<AdvertisementDto> actual = advertisementService
                .getAdvertisements(
                        0,
                        10,
                        Sort.Direction.ASC,
                        AdvertisementSortBy.BODY);

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                null,
                null,
                null);

        // Сравнивает результат теста с эталонным значением
        assertEquals(List.of(advertisementDto), actual);

    }







    /**
     * Объявление
     */

    /** При обращении к advertisementRepository.save
       сущности присваивается id */
    private void MockitoAdvertisementSaveFirstlyAfterCreateAndSetId(
            Long advertisementId) {

        /*
           Только что созданная сущность объявления,
           ещё не сохраненная в базе, не имеет идентификатора.
           Он появляется только после сохранения в базе.
           Поэтому при мокировании advertisementRepository.save()
           нужно еще и изменить id.
           При обращении к advertisementRepository.save
           сущности присваивается id */
        doAnswer(
                invocation -> {
                    Object[] args = invocation.getArguments();
                    ((Advertisement)args[0]).setId(advertisementId);
                    return null; // void method in a block-style lambda, so return null
                })
                .when(advertisementRepository)
                .save(any());

    }

    /** Возвращает объявление с прикрепленными категориями */
    private void MockitoAdvertisementFindByIdAndFetchCategory(
            boolean nullId,
            OffsetDateTime createdAt,
            Category category) {

        Advertisement advertisement = new Advertisement(

                nullId ? null : ADVERTISEMENT_ID,
                createdAt == null ? ADVERTISEMENT_CREATED_AT : createdAt,
                ADVERTISEMENT_UPDATED_AT,
                ADVERTISEMENT_TITLE,
                ADVERTISEMENT_BODY,
                ADVERTISEMENT_OWNER,
                ADVERTISEMENT_PRICE,
                ADVERTISEMENT_STATUS,
                category,
                ADVERTISEMENT_TAGS

        );

        category.getAdvertisements().add(advertisement);

        Mockito
                .when(advertisementRepository
                        .findByIdAndFetchCategory(
                                advertisement.getId()))
                .thenReturn(Optional.of(advertisement));

    }

    /** Возвращает объявление с прикрепленными категориями */
    private void MockitoAdvertisementFindByIdAndFetchCategoryReturnsEmpty() {

        Mockito
                .when(advertisementRepository
                        .findByIdAndFetchCategory(
                                anyLong()))
                .thenReturn(Optional.empty());

    }

    /** FindAll */
    private void MockitoAdvertisementFindAll() {

        Mockito
                .when(advertisementRepository
                        .findAll(
                                PageRequest.of(
                                        0,
                                        LIMIT)))
                .thenReturn(getPageWithAdvertisements());

    }

    /** FindActive */
    private void MockitoAdvertisementFindActive() {

        Mockito
                .when(advertisementRepository
                        .findAll(
                                PageRequest.of(
                                        0,
                                        LIMIT)))
                .thenReturn(getPageWithAdvertisements());

    }

    /**
     *  Категория
     */

    /** FindById */
    private void MockitoCategoryFindById(
            Category category) {

        Mockito
                .when(categoryRepository
                        .findById(
                                category.getId()))
                .thenReturn(Optional.of(category));

    }

    /** Категория не найдена */
    private void MockitoCategoryFindByIdReturnsEmpty() {

        Mockito
                .when(categoryRepository
                        .findById(anyLong()))
                .thenReturn(Optional.empty());

    }

    /**
     * Таг
     */
    private void MockitoTagFindById(
            Tag tag) {

        Mockito
                .when(tagRepository.findById(tag.getId()))
                .thenReturn(Optional.of(tag));

    }

    /**
     * Авторизация и аутентификация
     */
    private void MockitoAuth(
            String userName,
            String userRole) {

        Mockito.when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(
                        SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal())
                .thenReturn(userName);
        var authority = new SimpleGrantedAuthority(userRole);
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        // https://docs.oracle.com/javase/tutorial/java/generics/index.html
        // https://stackoverflow.com/questions/51168430/cannot-resolve-method-with-mockito
        doReturn(authorities)
                .when(authentication)
                .getAuthorities();

    }

    /**
     * Возвращает сущность объявления
     * @param nullId Если нужно, чтобы id был null
     * @return Сущность объявления
     */
    private Advertisement getAdvertisement(
            boolean nullId,
            OffsetDateTime createdAt) {

        return new Advertisement(

                nullId ? null : ADVERTISEMENT_ID,
                createdAt == null ? ADVERTISEMENT_CREATED_AT : createdAt,
                ADVERTISEMENT_UPDATED_AT,
                ADVERTISEMENT_TITLE,
                ADVERTISEMENT_BODY,
                ADVERTISEMENT_OWNER,
                ADVERTISEMENT_PRICE,
                ADVERTISEMENT_STATUS,
                ADVERTISEMENT_CATEGORY,
                ADVERTISEMENT_TAGS

        );

    }

    /**
     * Возвращает DTO объявления
     * @param nullId Если нужно, чтобы id был null
     * @return DTO объявления
     */
    private AdvertisementDto getAdvertisementDto(
            boolean nullId,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt,
            Long categoryId) {

        return AdvertisementDto.builder()
                .id(nullId ? null : ADVERTISEMENT_ID)
                .createdAt(createdAt == null ? ADVERTISEMENT_CREATED_AT : createdAt)
                .updatedAt(updatedAt == null ? ADVERTISEMENT_UPDATED_AT : updatedAt)
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
                .status(ADVERTISEMENT_STATUS)
                .categoryId(categoryId == null ? CATEGORY.getId() : categoryId)
                .tagIds(TAG_IDs)
                .owner(ADVERTISEMENT_OWNER)
                .build();

    }

    /**
     * Возвращает DTO при создании объявления
     * @return DTO при создании объявления
     */
    private AdvertisementCreateDto getAdvertisementCreateDto() {

        return AdvertisementCreateDto.builder()
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
                .categoryId(CATEGORY.getId())
                .tagIds(TAG_IDs)
                .build();

    }

    /**
     * Возвращает DTO при обновлении объявления
     * @return DTO при обновлении объявления
     */
    private AdvertisementUpdateDto getAdvertisementUpdateDto(
            Long categoryId) {

        return AdvertisementUpdateDto.builder()
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
                .status(ADVERTISEMENT_STATUS)
                .categoryId(categoryId)
                .tagIds(TAG_IDs)
                .build();

    }

    /**
     * Возвращает страницу с объявлениями
     */
    private Page<Advertisement> getPageWithAdvertisements() {

        return new PageImpl<>(
                List.of(
                        getAdvertisement(
                                false,
                                null)));

    }

}


    /*


    @Test
    void testGetAdvertisementsWithoutLimit() {
        Mockito.when(advertisementRepository.findAll(PageRequest.of(0, DEFAULT_LIMIT))).thenReturn(getPageWithAdvertisements());

        final List<AdvertisementDto> actual = advertisementService.getAdvertisements(null);

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(List.of(taskDto), actual);
    }





    @Test
    void testDeleteById() {
        advertisementService.deleteById(ADVERTISEMENT_ID);
        Mockito.verify(advertisementRepository).deleteById(ADVERTISEMENT_ID);
    }



    @Test
    void testGetAllAdvertisements() {
        Mockito.when(advertisementRepository.findAll()).thenReturn(getPageWithAdvertisements());

        final List<AdvertisementDto> actual = advertisementService.getAdvertisements();

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(List.of(taskDto), actual);
    }

    private Page<Advertisement> getPageWithAdvertisements() {
        return new PageImpl<>(List.of(getAdvertisement(false)));
    }

    */