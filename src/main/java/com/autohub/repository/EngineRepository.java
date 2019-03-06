package com.autohub.repository;

import com.autohub.domain.entity.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EngineRepository extends JpaRepository<Engine, String> {
}
