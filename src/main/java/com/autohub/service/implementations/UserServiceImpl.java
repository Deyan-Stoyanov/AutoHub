package com.autohub.service.implementations;

import com.autohub.domain.entity.User;
import com.autohub.domain.entity.UserRole;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.repository.UserRepository;
import com.autohub.service.interfaces.UserRoleService;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserRoleService userRoleService, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public UserServiceModel save(UserServiceModel userServiceModel) {
        try {
            User user = this.modelMapper.map(userServiceModel, User.class);
            user.setAuthorities(userServiceModel.getAuthorities()
                    .stream()
                    .map(userRoleServiceModel -> this.modelMapper.map(userRoleServiceModel, UserRole.class))
                    .collect(Collectors.toSet()));
            User saved = this.userRepository.save(user);
            return this.modelMapper.map(saved, UserServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        userServiceModel.setPassword(encoder.encode(userServiceModel.getPassword()));
        userServiceModel.setAuthorities(this.generateUserRole());
         return this.save(userServiceModel);
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
        User user = this.userRepository.findByUsername(username).orElse(null);
        return user == null ? null : this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel update(UserServiceModel userServiceModel) {
        UserServiceModel oldModel = this.findById(userServiceModel.getId());
        if (encoder.matches(oldModel.getPassword(), userServiceModel.getPassword())) return null;
        if (userServiceModel.getPassword() != null && !userServiceModel.getPassword().isEmpty()) {
            userServiceModel.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        } else {
            userServiceModel.setPassword(oldModel.getPassword());
        }
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setImageFileName(oldModel.getImageFileName());
        user.setAuthorities(oldModel.getAuthorities().stream().map(x -> this.modelMapper.map(x, UserRole.class)).collect(Collectors.toList()));
        User savedUser = this.userRepository.save(user);
        return this.modelMapper.map(savedUser, UserServiceModel.class);
    }

    @Override
    public UserServiceModel deleteById(String id) {
        UserServiceModel userServiceModel = this.modelMapper.map(this.userRepository.findById(id), UserServiceModel.class);
        this.userRepository.deleteById(id);
        return userServiceModel;
    }

    @Override
    public UserServiceModel switchRoleById(String id, UserRoleServiceModel userRoleServiceModel) {
        UserServiceModel model = this.modelMapper.map(this.userRepository.findById(id).get(), UserServiceModel.class);
        if (model.getAuthorities().stream().anyMatch(x -> x.getRole().equals(Role.ROLE_ROOT))) {
            return null;
        }
        model.setAuthorities(new HashSet<>() {{
            add(userRoleService.findByRole(userRoleServiceModel.getRole()));
        }});
        this.save(model);
        return model;
    }

    private Set<UserRoleServiceModel> generateUserRole() {
        Set<UserRoleServiceModel> roles = new HashSet<>();
        if (this.userRepository.count() == 0) {
            roles.add(this.modelMapper
                    .map(this.userRoleService.findByRole(Role.ROLE_ROOT), UserRoleServiceModel.class));
        } else {
            roles.add(this.modelMapper
                    .map(this.userRoleService.findByRole(Role.ROLE_USER), UserRoleServiceModel.class));
        }
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found."));
    }
}
