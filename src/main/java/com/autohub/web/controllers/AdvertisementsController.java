package com.autohub.web.controllers;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.binding.AdvertisementBindingModel;
import com.autohub.domain.model.binding.CarAdvertisementBindingModel;
import com.autohub.domain.model.binding.PartAdvertisementBindingModel;
import com.autohub.domain.model.service.*;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@Controller
public class AdvertisementsController {
    private final CarAdvertisementService carAdvertisementService;
    private final PartAdvertisementService partAdvertisementService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdvertisementsController(CarAdvertisementService carAdvertisementService, PartAdvertisementService partAdvertisementService, UserService userService, ModelMapper modelMapper) {
        this.carAdvertisementService = carAdvertisementService;
        this.partAdvertisementService = partAdvertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/marketplace")
    public ModelAndView marketplace(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("carAds", carAdvertisementService.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.addObject("partAds", partAdvertisementService.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                .collect(Collectors.toList()));
        return initData(modelAndView, session, "marketplace");
    }

    @GetMapping("/marketplace/advertisements/publish/car")
    public ModelAndView addCar(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        return initData(modelAndView, session, "add-car");
    }

    @PostMapping("/marketplace/advertisements/publish/car")
    public ModelAndView confirmAddCar(@ModelAttribute(name = "model") CarAdvertisementBindingModel model,
                                      ModelAndView modelAndView, HttpSession session) {
        model.setUser((String) session.getAttribute("username"));
        CarServiceModel carServiceModel = initCarServiceModel(model);
        AddressServiceModel addressServiceModel = initAddressServiceModel(model);
        CarAdvertisementServiceModel carAdvertisementServiceModel = initCarAdvertisementServiceModel(model, carServiceModel, addressServiceModel);
        CarAdvertisementServiceModel savedModel = this.carAdvertisementService.save(carAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.setViewName("redirect:/marketplace/advertisements/publish/car");
            return modelAndView;
        }
        return initData(modelAndView, session, "marketplace");
    }

    @PostMapping("/marketplace/advertisements/publish/part")
    public ModelAndView confirmAddPart(@ModelAttribute(name = "model") PartAdvertisementBindingModel model,
                                       ModelAndView modelAndView, HttpSession session) {
        model.setUser((String) session.getAttribute("username"));
        PartServiceModel partServiceModel = initPartServiceModel(model);
        AddressServiceModel addressServiceModel = initAddressServiceModel(model);
        PartAdvertisementServiceModel partAdvertisementServiceModel = initPartAdvertisementServiceModel(model, partServiceModel, addressServiceModel);
        PartAdvertisementServiceModel savedModel = this.partAdvertisementService.save(partAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.setViewName("redirect:/marketplace/advertisements/publish/part");
            return modelAndView;
        }
        return initData(modelAndView, session, "marketplace");
    }

    @GetMapping("/marketplace/advertisements/publish/part")
    public ModelAndView addPart(ModelAndView modelAndView, HttpSession session) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        return initData(modelAndView, session, "add-part");
    }

    @GetMapping("/marketplace/advertisements/{id}")
    public ModelAndView myAdvertisement(@PathVariable("id") String id, ModelAndView modelAndView, HttpSession session) {
        initData(modelAndView, session, "my-advertisements");
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

    @GetMapping("/marketplace/pending")
    public ModelAndView pending(ModelAndView modelAndView, HttpSession session) {
        initData(modelAndView, session, "pending");
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

    private ModelAndView initData(ModelAndView modelAndView, HttpSession session, String viewName) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName(viewName);
        }
        return modelAndView;
    }

    private PartAdvertisementServiceModel initPartAdvertisementServiceModel(PartAdvertisementBindingModel model, PartServiceModel partServiceModel, AddressServiceModel addressServiceModel) {
        PartAdvertisementServiceModel partAdvertisementServiceModel = new PartAdvertisementServiceModel();
        partAdvertisementServiceModel.setAddress(addressServiceModel);
        partAdvertisementServiceModel.setDescription(model.getDescription());
        partAdvertisementServiceModel.setPart(partServiceModel);
        partAdvertisementServiceModel.setPrice(model.getPrice());
        partAdvertisementServiceModel.setUser(this.userService.findByUsername(model.getUser()));
        return partAdvertisementServiceModel;
    }

    private PartServiceModel initPartServiceModel(PartAdvertisementBindingModel model) {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName(model.getName());
        partServiceModel.setManufacturer(model.getManufacturer());
        partServiceModel.setCarSuitableFor(model.getCarSuitableFor());
        return partServiceModel;
    }

    private CarAdvertisementServiceModel initCarAdvertisementServiceModel(CarAdvertisementBindingModel model, CarServiceModel carServiceModel, AddressServiceModel addressServiceModel) {
        CarAdvertisementServiceModel carAdvertisementServiceModel = new CarAdvertisementServiceModel();
        carAdvertisementServiceModel.setAddress(addressServiceModel);
        carAdvertisementServiceModel.setCar(carServiceModel);
        carAdvertisementServiceModel.setDescription(model.getDescription());
        carAdvertisementServiceModel.setPrice(model.getPrice());
        carAdvertisementServiceModel.setUser(this.userService.findByUsername(model.getUser()));
        return carAdvertisementServiceModel;
    }

    private AddressServiceModel initAddressServiceModel(AdvertisementBindingModel model) {
        AddressServiceModel addressServiceModel = new AddressServiceModel();
        addressServiceModel.setCity(model.getCity());
        addressServiceModel.setCountry(model.getCountry());
        addressServiceModel.setProvince(model.getProvince());
        return addressServiceModel;
    }

    private CarServiceModel initCarServiceModel(CarAdvertisementBindingModel model) {
        CarServiceModel carServiceModel = new CarServiceModel();
        EngineServiceModel engineServiceModel = new EngineServiceModel();
        engineServiceModel.setVolume(model.getVolume());
        engineServiceModel.setFuelType(model.getFuelType());
        engineServiceModel.setHorsepower(model.getHorsepower());
        engineServiceModel.setModification(model.getModification());
        carServiceModel.setEngine(engineServiceModel);
        carServiceModel.setColor(model.getColor());
        carServiceModel.setMake(model.getMake());
        carServiceModel.setModel(model.getModel());
        carServiceModel.setMileage(model.getMileage());
        carServiceModel.setProductionDate(model.getProductionDate());
        carServiceModel.setType(model.getCarType());
        return carServiceModel;
    }
}
