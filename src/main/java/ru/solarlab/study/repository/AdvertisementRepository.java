package ru.solarlab.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Advertisement;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Long> {

    /**
     * Возвращает объявление с прикрепленной категорией
     * @param id
     * @return
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
    /* Optional<> нужен для orElseThrow */


    /**
     * Возвращает объявление по тагу
     * @param tagId
     * @return
     */
    //List<Advertisement> findAdvertisementsByTagId(Long tagId);

}

//@Modifying