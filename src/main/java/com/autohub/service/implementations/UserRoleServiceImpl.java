package com.autohub.service.implementations;

import com.autohub.domain.entity.UserRole;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.repository.UserRoleRepository;
import com.autohub.service.interfaces.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper modelMapper) {
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserRoleServiceModel save(UserRoleServiceModel userRoleServiceModel) {
        UserRole userRole = this.userRoleRepository.save(this.modelMapper.map(userRoleServiceModel, UserRole.class));
        return this.modelMapper.map(userRole, UserRoleServiceModel.class);
    }

    @Override
    public List<UserRoleServiceModel> findAll() {
        return this.userRoleRepository.findAll()
                .stream()
                .map(userRole -> this.modelMapper.map(userRole, UserRoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleServiceModel findByRole(Role role) {
        UserRole userRole = this.userRoleRepository.findByRole(role);
        return userRole == null ? null : this.modelMapper.map(userRole, UserRoleServiceModel.class);
    }
}
