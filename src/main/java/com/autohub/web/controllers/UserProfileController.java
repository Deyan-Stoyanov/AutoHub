package com.autohub.web.controllers;

import com.autohub.domain.model.binding.UserEditBindingModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.domain.model.view.UserProfileViewModel;
import com.autohub.service.interfaces.UserService;
import com.autohub.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ModelAndView profile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = Util.findUser(id, this.userService, this.modelMapper);
        modelAndView.setViewName("profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public ModelAndView editProfile(@PathVariable("id") String id,
                                    @ModelAttribute(name = "user") UserEditBindingModel user,
                                    ModelAndView modelAndView) {
        modelAndView.addObject("user", this.modelMapper.map(this.userService.findById(id), UserProfileViewModel.class));
        modelAndView.setViewName("edit-profile");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
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
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserProfileViewModel user = Util.findUser(id, this.userService, this.modelMapper);
        modelAndView.setViewName("delete-profile");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ModelAndView confirmDeleteProfile(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.userService.deleteById(id);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
