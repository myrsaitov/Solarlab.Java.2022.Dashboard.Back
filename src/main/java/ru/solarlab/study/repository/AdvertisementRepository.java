package ru.solarlab.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Advertisement;

@Repository
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Long> {

    //List<Advertisement> findById(Integer id);

}