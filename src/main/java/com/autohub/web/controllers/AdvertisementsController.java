package com.autohub.web.controllers;

import com.autohub.domain.entity.User;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marketplace")
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

    @GetMapping("/")
    public ModelAndView marketplace(ModelAndView modelAndView) {
        return initData(modelAndView, "marketplace");
    }

    @GetMapping(value = "/fetch/cars", produces = "application/json")
    @ResponseBody
    public Object fetchCars() {
        return carAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.APPROVED))
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/fetch/parts", produces = "application/json")
    @ResponseBody
    public Object fetchParts() {
        return partAdvertisementService.findAll()
                .stream()
                .filter(advert -> advert.getStatus().equals(AdvertisementStatus.APPROVED))
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/publish/car")
    public ModelAndView addCar(@ModelAttribute(name = "advert") CarAdvertisementBindingModel model,
                               ModelAndView modelAndView) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        return initData(modelAndView, "add-car");
    }

    @PostMapping("/publish/car")
    public ModelAndView confirmAddCar(@RequestParam(value = "file", required = false) MultipartFile file,
                                      @Valid @ModelAttribute(name = "advert") CarAdvertisementBindingModel advert,
                                      BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-car");
            return modelAndView;
        }
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        CarServiceModel carServiceModel = initCarServiceModel(advert);
        AddressServiceModel addressServiceModel = initAddressServiceModel(advert);
        CarAdvertisementServiceModel carAdvertisementServiceModel = initCarAdvertisementServiceModel(advert, carServiceModel, addressServiceModel);
        CarAdvertisementServiceModel savedModel = this.carAdvertisementService.save(carAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.setViewName("redirect:/marketplace/advertisements/publish/car");
            return modelAndView;
        } else if (file != null) {
            String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\car_images";
            File f1 = new File(filePath + "\\" + savedModel.getId() + ".jpg");
            file.transferTo(f1);
        }
        return initData(modelAndView, "marketplace");
    }

    @GetMapping("/publish/part")
    public ModelAndView addPart(@ModelAttribute(name = "advert") PartAdvertisementBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        return initData(modelAndView, "add-part");
    }

    @PostMapping("/publish/part")
    public ModelAndView confirmAddPart(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @Valid @ModelAttribute(name = "advert") PartAdvertisementBindingModel advert,
                                       BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("add-part");
            return modelAndView;
        }
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        PartServiceModel partServiceModel = initPartServiceModel(advert);
        AddressServiceModel addressServiceModel = initAddressServiceModel(advert);
        PartAdvertisementServiceModel partAdvertisementServiceModel = initPartAdvertisementServiceModel(advert, partServiceModel, addressServiceModel);
        PartAdvertisementServiceModel savedModel = this.partAdvertisementService.save(partAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.setViewName("redirect:/marketplace/advertisements/publish/part");
            return modelAndView;
        } else if (file != null) {
            String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\part_images";
            File f1 = new File(filePath + "\\" + savedModel.getId() + "jpg");
            file.transferTo(f1);
        }
        return initData(modelAndView, "marketplace");
    }

    @GetMapping("/{id}")
    public ModelAndView myAdvertisement(@PathVariable("id") String id, ModelAndView modelAndView) {
        initData(modelAndView, "my-advertisements");
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

    @GetMapping("/pending")
    public ModelAndView pending(ModelAndView modelAndView) {
        initData(modelAndView, "pending");
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

    @GetMapping("/cars/approve/{id}")
    public ModelAndView approveCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.APPROVED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @GetMapping("/cars/decline/{id}")
    public ModelAndView declineCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.DECLINED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @GetMapping("/parts/approve/{id}")
    public ModelAndView approvePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.APPROVED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @GetMapping("/parts/decline/{id}")
    public ModelAndView declinePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.DECLINED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @GetMapping("/cars/details/{id}")
    public ModelAndView detailsCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("car-advertisement-details");
        modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
        return modelAndView;
    }

    @GetMapping("/parts/details/{id}")
    public ModelAndView detailsPart(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("part-advertisement-details");
        modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
        return modelAndView;
    }

    @GetMapping("/cars/edit/{id}")
    public ModelAndView editCar(@PathVariable("id") String id,
                                @ModelAttribute(name = "advert") CarAdvertisementBindingModel model,
                                ModelAndView modelAndView) {
        modelAndView.setViewName("edit-car-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
        return modelAndView;
    }

    @PostMapping("/cars/edit/{id}")
    public ModelAndView confirmEditCar(@PathVariable("id") String id,
                                       @Valid @ModelAttribute(name = "advert") CarAdvertisementBindingModel advert,
                                       BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-car-advertisement");
            modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
            return modelAndView;
        }
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        CarServiceModel carServiceModel = initCarServiceModel(advert);
        AddressServiceModel addressServiceModel = initAddressServiceModel(advert);
        CarAdvertisementServiceModel carAdvertisementServiceModel = initCarAdvertisementServiceModel(advert, carServiceModel, addressServiceModel);
        carAdvertisementServiceModel.setId(id);
        this.carAdvertisementService.update(carAdvertisementServiceModel);
        modelAndView.setViewName("redirect:/cars/details/" + id);
        return modelAndView;
    }


    @GetMapping("/parts/edit/{id}")
    public ModelAndView confirmEditPart(@PathVariable("id") String id,
                                        @ModelAttribute(name = "advert") PartAdvertisementBindingModel model,
                                        ModelAndView modelAndView) {
        modelAndView.setViewName("edit-part-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
        return modelAndView;
    }

    @PostMapping("/parts/edit/{id}")
    public ModelAndView editPart(@PathVariable("id") String id,
                                 @Valid @ModelAttribute(name = "advert") PartAdvertisementBindingModel advert,
                                 BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-part-advertisement" + id);
            modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
            return modelAndView;
        }
        PartServiceModel partServiceModel = initPartServiceModel(advert);
        AddressServiceModel addressServiceModel = initAddressServiceModel(advert);
        PartAdvertisementServiceModel partAdvertisementServiceModel = initPartAdvertisementServiceModel(advert, partServiceModel, addressServiceModel);
        partAdvertisementServiceModel.setId(id);
        modelAndView.setViewName("redirect:/parts/details/" + id);
        return modelAndView;
    }


    @GetMapping("/cars/delete/{id}")
    public ModelAndView deleteCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-car-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.carAdvertisementService.findById(id), CarAdvertisementViewModel.class));
        return modelAndView;
    }

    @PostMapping("/cars/delete/{id}")
    public ModelAndView confirmDeleteCar(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.carAdvertisementService.deleteById(id);
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }

    @GetMapping("/parts/delete/{id}")
    public ModelAndView deletePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-part-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
        return modelAndView;
    }

    @PostMapping("/parts/delete/{id}")
    public ModelAndView confirmDeletePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.deleteById(id);
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }

    private ModelAndView initData(ModelAndView modelAndView, String viewName) {
        modelAndView.setViewName(viewName);
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
