package com.autohub.service.interfaces;

import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserRoleServiceModel;

import java.util.List;

public interface UserRoleService {
    UserRoleServiceModel save(UserRoleServiceModel userRoleServiceModel);

    List<UserRoleServiceModel> findAll();

    UserRoleServiceModel findByRole(Role role);
}
