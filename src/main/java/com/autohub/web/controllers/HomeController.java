package com.autohub.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public ModelAndView home(ModelAndView modelAndView, HttpSession session) {
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView homeLoggedIn(ModelAndView modelAndView, HttpSession session, RedirectAttributes redirectAttributes) {
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
