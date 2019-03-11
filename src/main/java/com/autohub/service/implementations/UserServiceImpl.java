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

import java.util.List;
import java.util.stream.Collectors;

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
    public UserServiceModel register(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(DigestUtils.sha256Hex(userServiceModel.getPassword()));
        if (this.userRepository.findAll().isEmpty()) {
            userServiceModel.setRole(Role.ADMIN);
        } else {
            userServiceModel.setRole(Role.USER);
        }
        try {
            this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel, User.class));
            return userServiceModel;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserServiceModel login(UserServiceModel userServiceModel) {
        UserServiceModel user = this.modelMapper.map(this.findByUsername(userServiceModel.getUsername()), UserServiceModel.class);
        if (user != null && user.getPassword().equals(DigestUtils.sha256Hex(userServiceModel.getPassword()))) {
            return user;
        }
        return null;
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findById(String id) {
        User user = this.userRepository.findById(id).orElse(null);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel update(UserServiceModel userServiceModel) {
        this.userRepository.save(this.modelMapper.map(userServiceModel, User.class));
        return userServiceModel;
    }

    @Override
    public UserServiceModel deleteById(String id) {
        UserServiceModel userServiceModel = this.modelMapper.map(this.userRepository.findById(id), UserServiceModel.class);
        this.userRepository.deleteById(id);
        return userServiceModel;
    }

    @Override
    public UserServiceModel switchRoleById(String id) {
        UserServiceModel userServiceModel = this.findById(id);
        if (userServiceModel.getRole().equals(Role.ADMIN)) {
            userServiceModel.setRole(Role.USER);
        } else {
            userServiceModel.setRole(Role.ADMIN);
        }
        this.update(userServiceModel);
        return userServiceModel;
    }
}
