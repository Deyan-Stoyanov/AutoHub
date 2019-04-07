package com.autohub.repository;

import com.autohub.domain.entity.CarAdvertisement;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarAdvertisementRepository extends PagingAndSortingRepository<CarAdvertisement, String> {
    List<CarAdvertisement> findAllByUserId(String id);
}
