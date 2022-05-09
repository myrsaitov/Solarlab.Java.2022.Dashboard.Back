package ru.solarlab.study.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Category;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.mapper.AdvertisementMapperImpl;
import ru.solarlab.study.repository.AdvertisementRepository;
import ru.solarlab.study.repository.CategoryRepository;
import ru.solarlab.study.repository.TagRepository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
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
    public static final Long CATEGORY_ID = new Long(1L);
    public static final Category CATEGORY = new Category(
            CATEGORY_ID,
            OffsetDateTime.now(),
            null,
            "Category",
            CategoryStatus.ACTIVE,
            null,
            new HashSet<>(),
            new HashSet<>());

    public static final Category ADVERTISEMENT_CATEGORY = CATEGORY;

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
    public static final String USER_NAME = "user1";
    public static final String USER_ROLE = "USER";
    public static final UserDto USER_DTO = new UserDto(USER_NAME,USER_ROLE);
    @Mock
    Authentication authentication;// = Mockito.mock(Authentication.class);
    @Mock
    SecurityContext securityContext;// = Mockito.mock(SecurityContext.class);


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
     * Create
     */
    @Test
    void testCreate() throws NoSuchMethodException {

        /* Категория */
        Mockito.when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(CATEGORY));

        /* Таги */
        Mockito.when(tagRepository.findById(TAG_1.getId()))
                .thenReturn(Optional.of(TAG_1));
        Mockito.when(tagRepository.findById(TAG_2.getId()))
                .thenReturn(Optional.of(TAG_2));
        Mockito.when(tagRepository.findById(TAG_3.getId()))
                .thenReturn(Optional.of(TAG_3));

        /* При обращении к advertisementRepository.save
           сущности присваивается id */
        Advertisement advertisement = mock(Advertisement.class);
        doAnswer(
                invocation -> {
                    Object[] args = invocation.getArguments();
                    ((Advertisement)args[0]).setId(ADVERTISEMENT_ID);
                    return null; // void method in a block-style lambda, so return null
                }).when(advertisementRepository).save(any());

        /* Авторизация и аутентификация */
        Mockito.when(securityContext.getAuthentication())
                .thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                .thenReturn(USER_NAME);
        var authority = new SimpleGrantedAuthority(USER_ROLE);
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);
        // https://docs.oracle.com/javase/tutorial/java/generics/index.html
        // https://stackoverflow.com/questions/51168430/cannot-resolve-method-with-mockito
        doReturn(authorities)
                .when(authentication)
                .getAuthorities();

        // Вызов тестируемого метода
        final AdvertisementDto actual = advertisementService
                .create(getAdvertisementCreateDto());

        // Задаёт эталонное значение
        final AdvertisementDto advertisementDto = getAdvertisementDto(
                false,
                actual.getCreatedAt());

        // Сравнивает результат теста с эталонным значением
        assertEquals(advertisementDto, actual);

        // Вызывался ли данный метод с таким аргументом?
        var adv = getAdvertisement(
                true,
                actual.getCreatedAt());
        /* Mockito.verify(advertisementRepository)
                .save(getAdvertisement(
                        true,
                        actual.getCreatedAt()));*/
        // Разные HashSet у Set<Tag>, поэтому не получается повторить точь-в-точь
        Mockito.verify(advertisementRepository)
                .save(any());

        // Вызывался ли данный метод с таким аргументом?
        Mockito.verify(categoryRepository)
                .save(CATEGORY);

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
            OffsetDateTime createdAt) {

        return AdvertisementDto.builder()
                .id(nullId ? null : ADVERTISEMENT_ID)
                .createdAt(createdAt == null ? ADVERTISEMENT_CREATED_AT : createdAt)
                .updatedAt(ADVERTISEMENT_UPDATED_AT)
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
                .status(ADVERTISEMENT_STATUS)
                .categoryId(CATEGORY_ID)
                .tagId(TAG_IDs)
                .owner(ADVERTISEMENT_OWNER)
                .build();

    }

    /**
     * Возвращает DTO при создании объявления
     * @return
     */
    private AdvertisementCreateDto getAdvertisementCreateDto() {

        return AdvertisementCreateDto.builder()
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
                .categoryId(CATEGORY_ID)
                .tagId(TAG_IDs)
                .build();

    }


    /*
    @Test
    void testGetAdvertisementsWithLimit() {
        Mockito.when(advertisementRepository.findAll(PageRequest.of(0, LIMIT))).thenReturn(getPageWithAdvertisements());

        final List<AdvertisementDto> actual = advertisementService.getAdvertisements(LIMIT);

        final AdvertisementDto advertisementDto = getAdvertisementDto(false);
        assertEquals(List.of(advertisementDto), actual);
    }

    @Test
    void testGetAdvertisementsWithoutLimit() {
        Mockito.when(advertisementRepository.findAll(PageRequest.of(0, DEFAULT_LIMIT))).thenReturn(getPageWithAdvertisements());

        final List<AdvertisementDto> actual = advertisementService.getAdvertisements(null);

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(List.of(taskDto), actual);
    }

    @Test
    void testGetByNameLike() {
        final String nameForLike = "name for like";
        Mockito.when(advertisementRepository.findByNameLike(nameForLike)).thenReturn(List.of(getAdvertisement(false)));

        final List<AdvertisementDto> actual = advertisementService.getByNameLike(nameForLike);

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(List.of(taskDto), actual);
    }

    @Test
    void testGetById() {
        Mockito.when(advertisementRepository.findById(ADVERTISEMENT_ID)).thenReturn(Optional.of(getTask(false)));

        final AdvertisementDto actual = advertisementService.getById(ADVERTISEMENT_ID);

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(taskDto, actual);
    }

    @Test
    void testUpdate() {
        final AdvertisementDto actual = advertisementService.update(ADVERTISEMENT_ID, getAdvertisementUpdateDto());

        final AdvertisementDto taskDto = getAdvertisementDto(false);
        assertEquals(taskDto, actual);
        Mockito.verify(advertisementRepository).save(getTask(false));
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





    private AdvertisementUpdateDto getAdvertisementUpdateDto() {
        return AdvertisementUpdateDto.builder()
                .name(ADVERTISEMENT_NAME)
                .startedAt(ADVERTISEMENT_STARTED_AT)
                .status(ADVERTISEMENT_STATUS)
                .build();
    }


    */

}