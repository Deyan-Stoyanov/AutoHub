package com.autohub.web.controllers;

import com.autohub.domain.entity.User;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.domain.model.view.UserProfileViewModel;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserManagementController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserManagementController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/switch/to-admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_ADMIN);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/switch/to-user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_USER);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("")
    public ModelAndView users(ModelAndView modelAndView, Authentication authentication) {
        modelAndView.setViewName("users");
        String id = ((User) authentication.getPrincipal()).getId();
        modelAndView.addObject("users", mapUserViewModels(id));
        addUserRoles(modelAndView);
        return modelAndView;
    }

    private ModelAndView changeRoleOfUser(ModelAndView modelAndView, String id, Role role) {
        this.userService.switchRoleById(id, new UserRoleServiceModel() {{
            setRole(role);
        }});
        modelAndView.setViewName("redirect:/users");
        return modelAndView;
    }

    private void addUserRoles(ModelAndView modelAndView) {
        modelAndView.addObject("rootrole", Role.ROLE_ROOT);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("userrole", Role.ROLE_USER);
    }

    private List<UserProfileViewModel> mapUserViewModels(String id) {
        List<UserServiceModel> allUsers = this.userService.findAll();
        return allUsers.stream()
                .filter(user -> user.getAuthorities().stream().noneMatch(a -> a.getRole().equals(Role.ROLE_ROOT))
                        && !user.getId().equals(id))
                .map(user -> {
                    UserProfileViewModel model = this.modelMapper.map(user, UserProfileViewModel.class);
                    List<Role> roles = user.getAuthorities()
                            .stream()
                            .map(UserRoleServiceModel::getRole)
                            .collect(Collectors.toList());
                    model.setAuthorities(roles);
                    return model;
                })
                .collect(Collectors.toList());
    }
}
