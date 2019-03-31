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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView confirmRegister(@RequestParam(value = "file", required = false) MultipartFile file,
                                        @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                        ModelAndView modelAndView) throws IOException {
        if (model.getConfirmPassword().equals(model.getPassword())) {
            UserServiceModel registeredModel = this.userService.register(this.modelMapper.map(model, UserServiceModel.class));
            if (registeredModel != null && file != null) {
                String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\user_images";
                File f1 = new File(filePath + "\\" + registeredModel.getId() + ".jpg");
                file.transferTo(f1);
            }
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("redirect:/register");
        }
        return modelAndView;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = this.findUser(id);
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/profile/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = this.findUser(id);
        modelAndView.setViewName("edit-profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/profile/delete/{id}")
    public ModelAndView deleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = this.findUser(id);
        modelAndView.setViewName("delete-profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PostMapping("/profile/edit/{id}")
    public ModelAndView confirmEditProfile(@PathVariable("id") String id,
                                           @ModelAttribute(name = "model") UserEditBindingModel model,
                                           ModelAndView modelAndView) {
        if (model.getOldPassword() == null || !model.getPassword().equals(model.getConfirmPassword())) {
            modelAndView.setViewName("redirect:/profile/edit/" + id);
        } else {

            UserServiceModel userServiceModel =  this.modelMapper.map(model, UserServiceModel.class);
            userServiceModel.setId(id);
            this.userService.update(userServiceModel);
            modelAndView.setViewName("redirect:/profile/" + id);
        }
        return modelAndView;
    }

    @PostMapping("/profile/delete/{id}")
    public ModelAndView confirmDeleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteById(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/admin/users/switch/to-admin/{id}")
    public ModelAndView makeAdmin(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_ADMIN);
    }

    @GetMapping("/admin/users/switch/to-user/{id}")
    public ModelAndView makeUser(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        return changeRoleOfUser(modelAndView, id, Role.ROLE_USER);
    }

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
