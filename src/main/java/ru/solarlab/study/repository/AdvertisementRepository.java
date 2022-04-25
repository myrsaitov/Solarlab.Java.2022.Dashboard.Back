package ru.solarlab.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Advertisement;

import java.util.List;

@Repository
public interface AdvertisementRepository extends PagingAndSortingRepository<Advertisement, Integer> {

    //List<Advertisement> findById(Integer id);

}