package com.autohub.web.controllers;

import com.autohub.domain.entity.User;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.binding.AdvertisementBindingModel;
import com.autohub.domain.model.binding.CarAdvertisementBindingModel;
import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.UserService;
import com.autohub.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/marketplace/cars")
public class CarAdvertisementController {
    private final CarAdvertisementService carAdvertisementService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarAdvertisementController(CarAdvertisementService carAdvertisementService, UserService userService, ModelMapper modelMapper) {
        this.carAdvertisementService = carAdvertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/publish")
    public ModelAndView addCar(@ModelAttribute(name = "advert") CarAdvertisementBindingModel model,
                               ModelAndView modelAndView) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        modelAndView.setViewName("add-car");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/publish")
    public ModelAndView confirmAddCar(@RequestParam(value = "file", required = false) MultipartFile file,
                                      @Valid @ModelAttribute(name = "advert") CarAdvertisementBindingModel advert,
                                      BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) throws IOException {
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("carTypes", CarType.values());
            modelAndView.addObject("fuelTypes", FuelType.values());
            modelAndView.addObject("advert", advert);
            modelAndView.setViewName("add-car");
            return modelAndView;
        }
        CarServiceModel carServiceModel = Util.initCarServiceModel(advert);
        AddressServiceModel addressServiceModel = Util.initAddressServiceModel(advert);
        CarAdvertisementServiceModel carAdvertisementServiceModel = Util.initCarAdvertisementServiceModel(advert, carServiceModel, addressServiceModel, this.userService);
        CarAdvertisementServiceModel savedModel = this.carAdvertisementService.save(carAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.addObject("carTypes", CarType.values());
            modelAndView.addObject("fuelTypes", FuelType.values());
            modelAndView.addObject("advert", advert);
            modelAndView.setViewName("add-car");
            return modelAndView;
        }
        if (file != null) {
            try {
                String filePath = "C:\\Users\\Lenovo\\autohub\\images\\car_images";
                String fullPath = filePath + "\\" + savedModel.getId() + "." + file.getOriginalFilename().split("\\.")[1];
                File f1 = new File(fullPath);
                file.transferTo(f1);
                savedModel.setImageFileName(fullPath.substring(fullPath.lastIndexOf("\\") + 1));
                this.carAdvertisementService.save(savedModel);
            } catch (Exception ignored) {
            }
        }
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/approve/{id}")
    public ModelAndView approveCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.APPROVED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/decline/{id}")
    public ModelAndView declineCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.DECLINED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details/{id}")
    public ModelAndView detailsCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        CarAdvertisementServiceModel car = this.carAdvertisementService.findById(id);
        if (car == null) {
            modelAndView.setViewName("redirect:/marketplace");
        } else {
            modelAndView.setViewName("car-advertisement-details");
            modelAndView.addObject("advert", this.modelMapper.map(car, CarAdvertisementViewModel.class));
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public ModelAndView editCar(@PathVariable("id") String id,
                                @ModelAttribute(name = "advert") CarAdvertisementBindingModel model,
                                ModelAndView modelAndView) {
        CarAdvertisementServiceModel car = this.carAdvertisementService.findById(id);
        if (car == null) {
            modelAndView.setViewName("redirect:/marketplace");
        } else {
            modelAndView.setViewName("edit-car-advertisement");
            CarAdvertisementBindingModel bindingModel = this.modelMapper.map(car, CarAdvertisementBindingModel.class);
            Util.setUpBindingModel(bindingModel, car);
            modelAndView.addObject("advert", bindingModel);
            modelAndView.addObject("carTypes", CarType.values());
            modelAndView.addObject("fuelTypes", FuelType.values());
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public ModelAndView confirmEditCar(@PathVariable("id") String id,
                                       @Valid @ModelAttribute(name = "advert") CarAdvertisementBindingModel advert,
                                       BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) {
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-car-advertisement");
            modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
            return modelAndView;
        }
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        CarServiceModel carServiceModel = Util.initCarServiceModel(advert);
        AddressServiceModel addressServiceModel = Util.initAddressServiceModel(advert);
        CarAdvertisementServiceModel carAdvertisementServiceModel = Util.initCarAdvertisementServiceModel(advert, carServiceModel, addressServiceModel, this.userService);
        carAdvertisementServiceModel.setId(id);
        carAdvertisementServiceModel.setImageFileName(this.carAdvertisementService.findById(id).getImageFileName());
        this.carAdvertisementService.save(carAdvertisementServiceModel);
        modelAndView.setViewName("redirect:/marketplace/cars/details/" + id);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-car-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ModelAndView confirmDeleteCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.deleteById(id);
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }
}
