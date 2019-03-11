package com.autohub.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactsController {
    @GetMapping("/about")
    public ModelAndView about(ModelAndView modelAndView) {
        modelAndView.setViewName("about");
        return modelAndView;
    }

    @GetMapping("/contacts")
    public ModelAndView contacts(ModelAndView modelAndView) {
        modelAndView.setViewName("contacts");
        return modelAndView;
    }
}
