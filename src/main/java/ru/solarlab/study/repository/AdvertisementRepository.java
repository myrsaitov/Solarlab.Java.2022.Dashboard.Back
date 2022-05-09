package ru.solarlab.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Advertisement;

import java.util.Optional;

/**
 * Репозиторий объявлений
 */
@Repository
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Long> {

    /**
     * Возвращает категории с активным статусом
     */
    @Query("SELECT a FROM Advertisement a WHERE a.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findActive(
            Pageable pageable);

    /**
     * Возвращает объявление с прикрепленной категорией
     * @param id Идентификатор объявления
     * @return Сущность объявления
     */
    @Query("SELECT a FROM Advertisement a JOIN FETCH a.category WHERE a.id = (:id)")
        /* Прикрепляет связанные объекты */
    Optional<Advertisement> findByIdAndFetchCategory(@Param("id") Long id);
        /* Optional<> нужен для orElseThrow */

    /**
     * Возвращает объявления с фильтром по категории
     */
    @Query("SELECT a FROM Advertisement a WHERE a.category.id = (:categoryId)")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findAllByCategory(
            Pageable pageable,
            @Param("categoryId") Long categoryId);

    /**
     * Возвращает активные объявления с фильтром по категории
     */
    @Query("SELECT a FROM Advertisement a WHERE a.category.id = (:categoryId) AND a.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findActiveByCategory(
            Pageable pageable,
            @Param("categoryId") Long categoryId);

    /**
     * Возвращает объявления с фильтром по тагу
     */
    @Query("SELECT DISTINCT a FROM Advertisement a JOIN a.tags tag WHERE tag.id = (:tagId)")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findAllByTag(
            Pageable pageable,
            @Param("tagId") Long tagId);

    /**
     * Возвращает активные объявления с фильтром по тагу
     */
    @Query("SELECT DISTINCT a FROM Advertisement a JOIN a.tags tag WHERE tag.id = (:tagId) AND a.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findActiveByTag(
            Pageable pageable,
            @Param("tagId") Long tagId);

    /**
     * Возвращает объявления с фильтром по владельцу
     */
    @Query("SELECT DISTINCT a FROM Advertisement a WHERE a.owner = (:owner)")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findAllByOwner(
            Pageable pageable,
            @Param("owner") String owner);

    /**
     * Возвращает активные объявления с фильтром по владельцу
     */
    @Query("SELECT DISTINCT a FROM Advertisement a WHERE a.owner = (:owner) AND a.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Advertisement> findActiveByOwner(
            Pageable pageable,
            @Param("owner") String owner);

}