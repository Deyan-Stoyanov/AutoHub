package com.autohub.web.controllers;

import com.autohub.domain.enums.Role;
import com.autohub.domain.model.binding.UserLoginBindingModel;
import com.autohub.domain.model.binding.UserRegisterBindingModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.domain.model.view.UserProfileViewModel;
import com.autohub.service.interfaces.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView confirmRegister(@ModelAttribute(name = "model") UserRegisterBindingModel model, ModelAndView modelAndView) {
        if (!model.getConfirmPassword().equals(model.getPassword()) ||
                this.userService.register(this.modelMapper.map(model, UserServiceModel.class)) == null) {
            modelAndView.setViewName("redirect:/register");
        } else {
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView confirmLogin(@ModelAttribute(name = "model") UserLoginBindingModel model,
                                     ModelAndView modelAndView, HttpSession session) {
        UserServiceModel userServiceModel = this.userService.login(this.modelMapper.map(model, UserServiceModel.class));
        if (userServiceModel == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            session.setAttribute("userId", userServiceModel.getId());
            session.setAttribute("username", userServiceModel.getUsername());
            session.setAttribute("isAdmin", userServiceModel.getRole().equals(Role.ADMIN));
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView profile(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            UserProfileViewModel user = this.findUser(id);
            modelAndView.setViewName("profile");
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }

    @GetMapping("/profile/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            UserProfileViewModel user = this.findUser(id);
            modelAndView.setViewName("edit-profile");
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }

    @GetMapping("/profile/delete/{id}")
    public ModelAndView deleteProfile(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            UserProfileViewModel user = this.findUser(id);
            modelAndView.setViewName("delete-profile");
            modelAndView.addObject("user", user);
        }
        return modelAndView;
    }

    @PostMapping("/profile/edit/{id}")
    public ModelAndView confirmEditProfile(@PathVariable("id") String id,
                                           @ModelAttribute(name = "model") UserRegisterBindingModel model,
                                           ModelAndView modelAndView) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        if (model.getPassword() != null && model.getPassword().equals(model.getConfirmPassword())) {
            userServiceModel.setPassword(DigestUtils.sha256Hex(model.getPassword()));
        }
        userServiceModel.setAge(model.getAge());
        userServiceModel.setEmail(model.getEmail());
        userServiceModel.setPhoneNumber(model.getPhoneNumber());
        userServiceModel.setFirstName(model.getFirstName());
        userServiceModel.setLastName(model.getLastName());
        userServiceModel.setGender(model.getGender());
        this.userService.update(userServiceModel);
        modelAndView.setViewName("redirect:/profile/" + userServiceModel.getId());
        return modelAndView;
    }

    @PostMapping("/profile/delete/{id}")
    public ModelAndView confirmDeleteProfile(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        this.userService.deleteById(id);
        if (session.getAttribute("userId").equals(id)) {
            session.invalidate();
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/profile/switch/{id}")
    public ModelAndView switchRole(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        this.userService.switchRoleById(id);
        modelAndView.setViewName("redirect:/admin/users");
        return modelAndView;
    }

    @GetMapping("/admin/users")
    public ModelAndView users(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("isAdmin") != null && (boolean) session.getAttribute("isAdmin")) {
            String currentUsername = (String) session.getAttribute("username");
            List<UserProfileViewModel> users = this.userService.findAll()
                    .stream()
                    .filter(user -> !user.getUsername().equals(currentUsername))
                    .map(user -> this.modelMapper.map(user, UserProfileViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.setViewName("users");
            modelAndView.addObject("users", users);
        } else {
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    private UserProfileViewModel findUser(String id) {
        UserServiceModel userServiceModel = this.userService.findById(id);
        if (userServiceModel == null) {
            throw new IllegalArgumentException("User not found");
        }
        return this.modelMapper.map(userServiceModel, UserProfileViewModel.class);
    }
}
