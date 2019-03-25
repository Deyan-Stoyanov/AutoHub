package com.autohub.repository;

import com.autohub.domain.entity.UserRole;
import com.autohub.domain.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByRole(Role role);
}
