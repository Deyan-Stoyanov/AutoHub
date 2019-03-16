package com.autohub.web.controllers;

import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@Controller
public class AdvertisementsController {
    private final CarAdvertisementService carAdvertisementService;
    private final PartAdvertisementService partAdvertisementService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdvertisementsController(CarAdvertisementService carAdvertisementService, PartAdvertisementService partAdvertisementService, ModelMapper modelMapper) {
        this.carAdvertisementService = carAdvertisementService;
        this.partAdvertisementService = partAdvertisementService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/marketplace")
    public ModelAndView marketplace(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("marketplace");
            modelAndView.addObject("carAds", carAdvertisementService.findAll()
                    .stream()
                    .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                    .collect(Collectors.toList()));
            modelAndView.addObject("partAds", partAdvertisementService.findAll()
                    .stream()
                    .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                    .collect(Collectors.toList()));
        }
        return modelAndView;
    }
}
