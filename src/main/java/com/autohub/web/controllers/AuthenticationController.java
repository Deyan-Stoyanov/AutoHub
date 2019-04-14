package com.autohub.web.controllers;

import com.autohub.domain.model.binding.UserRegisterBindingModel;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
public class AuthenticationController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthenticationController(UserService userService, ModelMapper modelMapper) {
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
        if (this.userService.findAll().stream().anyMatch(x -> x.getUsername().equals(user.getUsername()))) {
            modelAndView.setViewName("register");
        } else if (user.getConfirmPassword().equals(user.getPassword()) && !bindingResult.hasErrors()) {
            UserServiceModel registeredModel = this.userService.register(this.modelMapper.map(user, UserServiceModel.class));
            if (registeredModel != null && file != null) {
                try {
                    String filePath = "C:\\Users\\Lenovo\\autohub\\images\\user_images";
                    String fullPath = filePath + "\\" + registeredModel.getId() + "." + file.getOriginalFilename().split("\\.")[1];
                    File f1 = new File(fullPath);
                    file.transferTo(f1);
                    registeredModel.setImageFileName(fullPath.substring(fullPath.lastIndexOf("\\") + 1));
                    this.userService.save(registeredModel);
                } catch (Exception ignored) { }
            }
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }
}
