package com.autohub.service.implementations;

import com.autohub.domain.entity.User;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.repository.UserRepository;
import com.autohub.service.interfaces.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean register(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        if (this.userRepository.findAll().isEmpty()) {
            userServiceModel.setRole(Role.ADMIN);
        } else {
            userServiceModel.setRole(Role.USER);
        }
        try {
            this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean login(UserServiceModel userServiceModel) {
        UserServiceModel user = this.modelMapper.map(this.userRepository.findByUsername(userServiceModel.getUsername()), UserServiceModel.class);
        return user != null && user.getPassword().equals(DigestUtils.sha256Hex(userServiceModel.getPassword()));
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }
}
