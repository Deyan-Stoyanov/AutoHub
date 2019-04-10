package com.autohub.web.controllers;

import com.autohub.domain.entity.User;
import com.autohub.domain.enums.Role;
import com.autohub.domain.model.binding.UserEditBindingModel;
import com.autohub.domain.model.binding.UserRegisterBindingModel;
import com.autohub.domain.model.service.UserRoleServiceModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.domain.model.view.UserProfileViewModel;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute(name = "user") UserRegisterBindingModel model,
                                 ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/register")
    public ModelAndView confirmRegister(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @Valid @ModelAttribute(name = "user") UserRegisterBindingModel user,
                                        BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        if (user.getConfirmPassword().equals(user.getPassword()) && !bindingResult.hasErrors()) {
            UserServiceModel registeredModel = this.userService.register(this.modelMapper.map(user, UserServiceModel.class));
            if (registeredModel != null && file != null) {
                String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\user_images";
                File f1 = new File(filePath + "\\" + registeredModel.getId() + ".jpg");
                file.transferTo(f1);
            }
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = this.findUser(id);
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id,
                                    @ModelAttribute(name = "user") UserEditBindingModel user,
                                    ModelAndView modelAndView) {
        UserProfileViewModel userProfileViewModel = this.findUser(id);
        modelAndView.setViewName("edit-profile");
        modelAndView.addObject("user", userProfileViewModel);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/edit/{id}")
    public ModelAndView confirmEditProfile(@PathVariable("id") String id,
                                           @Valid @ModelAttribute(name = "user") UserEditBindingModel user,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (user.getOldPassword() == null || !user.getPassword().equals(user.getConfirmPassword())) {
            modelAndView.setViewName("redirect:/profile/edit/" + id);
        } else if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-profile");
        } else {
            UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
            userServiceModel.setId(id);
            this.userService.update(userServiceModel);
            modelAndView.setViewName("redirect:/profile/" + id);
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile/delete/{id}")
    public ModelAndView deleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = this.findUser(id);
        modelAndView.setViewName("delete-profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile/delete/{id}")
    public ModelAndView confirmDeleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteById(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/admin/users/switch/to-admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_ADMIN);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/admin/users/switch/to-user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_USER);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/admin/users")
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
        modelAndView.setViewName("redirect:/admin/users");
        return modelAndView;
    }

    private void addUserRoles(ModelAndView modelAndView) {
        modelAndView.addObject("rootrole", Role.ROLE_ROOT);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("adminrole", Role.ROLE_ADMIN);
        modelAndView.addObject("userrole", Role.ROLE_USER);
    }

    private UserProfileViewModel findUser(String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        if (userServiceModel == null) {
            throw new IllegalArgumentException("User not found");
        }
        return this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
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
