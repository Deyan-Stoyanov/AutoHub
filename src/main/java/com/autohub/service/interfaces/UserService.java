package com.autohub.service.interfaces;

import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.domain.model.service.UserServiceModel;

import java.util.List;

public interface UserService {
    UserServiceModel register(UserServiceModel map);

    UserServiceModel save(UserServiceModel userServiceModel);

    List<UserServiceModel> findAll();

    UserServiceModel findById(String id);

    UserServiceModel findByUsername(String username);

    UserServiceModel update(UserServiceModel userServiceModel);

    UserServiceModel deleteById(String id);

    UserServiceModel switchRoleById(String id, UserRoleServiceModel userRoleServiceModel);
}
