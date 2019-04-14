package com.autohub.web.controllers;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.UserServiceModel;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fetch")
public class DataFetchController {
    private final CarAdvertisementService carAdvertisementService;
    private final PartAdvertisementService partAdvertisementService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public DataFetchController(CarAdvertisementService carAdvertisementService, ModelMapper modelMapper, PartAdvertisementService partAdvertisementService, UserService userService) {
        this.carAdvertisementService = carAdvertisementService;
        this.partAdvertisementService = partAdvertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/cars", produces = "application/json")
    @ResponseBody
    public Object fetchCars() {
        return carAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.APPROVED))
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/parts", produces = "application/json")
    @ResponseBody
    public Object fetchParts() {
        return partAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.APPROVED))
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                .collect(Collectors.toList());
    }
}
