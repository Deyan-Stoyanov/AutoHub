package com.autohub.web.controllers;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.implementations.CarAdvertisementServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("fetch")
public class DataFetchController {
    private final CarAdvertisementServiceImpl carAdvertisementService;
    private final ModelMapper modelMapper;
    private final CarAdvertisementServiceImpl partAdvertisementService;

    @Autowired
    public DataFetchController(CarAdvertisementServiceImpl carAdvertisementService, ModelMapper modelMapper, CarAdvertisementServiceImpl partAdvertisementService) {
        this.carAdvertisementService = carAdvertisementService;
        this.modelMapper = modelMapper;
        this.partAdvertisementService = partAdvertisementService;
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
