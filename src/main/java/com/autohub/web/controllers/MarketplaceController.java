package com.autohub.web.controllers;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/marketplace")
public class MarketplaceController {

    private final CarAdvertisementService carAdvertisementService;
    private final ModelMapper modelMapper;
    private final PartAdvertisementService partAdvertisementService;

    @Autowired
    public MarketplaceController(CarAdvertisementService carAdvertisementService, ModelMapper modelMapper, PartAdvertisementService partAdvertisementService) {
        this.carAdvertisementService = carAdvertisementService;
        this.modelMapper = modelMapper;
        this.partAdvertisementService = partAdvertisementService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ModelAndView marketplace(ModelAndView modelAndView) {
        modelAndView.setViewName("marketplace");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ModelAndView myAdvertisement(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("my-advertisements");
        modelAndView.addObject("cars", this.carAdvertisementService.findAllByUserId(id)
                .stream()
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.addObject("parts", this.partAdvertisementService.findAllByUserId(id)
                .stream()
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pending")
    public ModelAndView pending(ModelAndView modelAndView) {
        modelAndView.setViewName("pending");
        modelAndView.addObject("cars", this.carAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.PENDING))
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.addObject("parts", this.partAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.PENDING))
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        return modelAndView;
    }

}
