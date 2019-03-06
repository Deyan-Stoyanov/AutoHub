package com.autohub.repository;

import com.autohub.domain.entity.CarAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAdvertismentRepository extends JpaRepository<CarAdvertisement, String> {
}
