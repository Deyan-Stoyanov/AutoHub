package com.autohub.service.interfaces;

import com.autohub.domain.model.service.UserServiceModel;

public interface UserService {
    boolean register(UserServiceModel map);

    boolean login(UserServiceModel userServiceModel);

    UserServiceModel findById(String id);
}
