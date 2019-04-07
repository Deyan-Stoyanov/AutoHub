package com.autohub.repository;

import com.autohub.domain.entity.PartAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartAdvertisementRepository extends PagingAndSortingRepository<PartAdvertisement, String> {
    List<PartAdvertisement> findAllByUserId(String id);
}
