package com.autohub.repository;

import com.autohub.domain.entity.PartAdvertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartAdvertismentRepository extends JpaRepository<PartAdvertisement, String> {
}
