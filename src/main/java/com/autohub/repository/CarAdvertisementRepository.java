package com.autohub.repository;

import com.autohub.domain.entity.CarAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarAdvertisementRepository extends JpaRepository<CarAdvertisement, String> {
    List<CarAdvertisement> findAllByUserId(String id);
}
