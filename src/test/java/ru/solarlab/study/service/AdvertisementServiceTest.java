package ru.solarlab.study.service;

import org.junit.jupiter.api.BeforeEach;
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

    public static final Long ADVERTISEMENT_ID = new Long(1);
    public static final OffsetDateTime ADVERTISEMENT_CREATED_AT = OffsetDateTime.now();
    public static final OffsetDateTime ADVERTISEMENT_UPDATED_AT = null;
    public static final String ADVERTISEMENT_TITLE = "title";
    public static final String ADVERTISEMENT_BODY = "body";
    public static final String ADVERTISEMENT_OWNER = "user1";
    public static final float ADVERTISEMENT_PRICE = (float) 10.1;
    public static final AdvertisementStatus ADVERTISEMENT_STATUS = AdvertisementStatus.NEW;
    public static final Category ADVERTISEMENT_CATEGORY = null;
    public static final Set<Tag> ADVERTISEMENT_TAGS = null;

    public static final Long CATEGORY_ID = new Long(1);
    public static final Category CATEGORY = new Category(
            CATEGORY_ID,
            OffsetDateTime.now(),
            null,
            "Category",
            CategoryStatus.ACTIVE,
            null,
            new HashSet<>(),
            new HashSet<>());

    public static final Long TAG_IDs[] = new Long[]{1L,2L,3L};
    public static final Long TAG_ID = new Long(1);
    public static final Tag TAG = new Tag(
            TAG_ID,
            OffsetDateTime.now(),
            null,
            "TagText",
            TagStatus.ACTIVE,
            new HashSet<>()
            );

    public static final String USER_NAME = "user1";
    public static final String USER_ROLE = "USER";
    public static final UserDto USER_DTO = new UserDto(USER_NAME,USER_ROLE);


    @Mock
    private AdvertisementRepository advertisementRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;

    //@Mock
    Authentication authentication;// = Mockito.mock(Authentication.class);
    //@Mock
    SecurityContext securityContext;// = Mockito.mock(SecurityContext.class);

    @Spy
    private AdvertisementMapper advertisementMapper = new AdvertisementMapperImpl();

    @InjectMocks
    private AdvertisementService advertisementService;

    @BeforeEach
    public void setup(){

        //MockitoAnnotations.initMocks(this); //without this you will get NPE
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

    }

    /**
     * Create
     */
    @Test
    void testCreate() throws NoSuchMethodException {

        when(
                        categoryRepository.findById(anyLong()))
                .thenReturn(Optional.of(CATEGORY));

        when(
                        tagRepository.findById(anyLong()))
                .thenReturn(Optional.of(TAG));


        Advertisement advertisement = mock(Advertisement.class);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((Advertisement)args[0]).setId(ADVERTISEMENT_ID);
            return null; // void method in a block-style lambda, so return null
        }).when(advertisementRepository).save(any());





        Authentication authentication = mock(Authentication.class);

        var a =authentication.getAuthorities();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(USER_NAME);

        var authority = new SimpleGrantedAuthority(USER_ROLE);
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(authority);

        // https://docs.oracle.com/javase/tutorial/java/generics/index.html
        // https://stackoverflow.com/questions/51168430/cannot-resolve-method-with-mockito
        doReturn(authorities).when(authentication).getAuthorities();


        final AdvertisementDto actual = advertisementService
                .create(getAdvertisementCreateDto());

        final AdvertisementDto advertisementDto = getAdvertisementDto(false);

        assertEquals(advertisementDto, actual);
        Mockito.verify(advertisementRepository).save(getAdvertisement(false));

    }

    /**
     * Возвращает сущность объявления
     * @param nullId Если нужно, чтобы id был null
     * @return Сущность объявления
     */
    private Advertisement getAdvertisement(
            boolean nullId) {

        return new Advertisement(

                nullId ? null : ADVERTISEMENT_ID,
                ADVERTISEMENT_CREATED_AT,
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
            boolean nullId) {

        return AdvertisementDto.builder()
                .id(nullId ? null : ADVERTISEMENT_ID)
                .createdAt(ADVERTISEMENT_CREATED_AT)
                .updatedAt(ADVERTISEMENT_UPDATED_AT)
                .title(ADVERTISEMENT_TITLE)
                .body(ADVERTISEMENT_BODY)
                .price(ADVERTISEMENT_PRICE)
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