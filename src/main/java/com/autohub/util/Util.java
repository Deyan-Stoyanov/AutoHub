package com.autohub.util;

import com.autohub.domain.model.binding.AdvertisementBindingModel;
import com.autohub.domain.model.binding.CarAdvertisementBindingModel;
import com.autohub.domain.model.binding.PartAdvertisementBindingModel;
import com.autohub.domain.model.service.*;
import com.autohub.domain.model.view.UserProfileViewModel;
import com.autohub.service.interfaces.UserService;
import org.modelmapper.ModelMapper;

public final class Util {
    public static PartAdvertisementServiceModel initPartAdvertisementServiceModel(PartAdvertisementBindingModel model, PartServiceModel partServiceModel, AddressServiceModel addressServiceModel, UserService userService) {
        PartAdvertisementServiceModel partAdvertisementServiceModel = new PartAdvertisementServiceModel();
        partAdvertisementServiceModel.setAddress(addressServiceModel);
        partAdvertisementServiceModel.setDescription(model.getDescription());
        partAdvertisementServiceModel.setPart(partServiceModel);
        partAdvertisementServiceModel.setPrice(model.getPrice());
        partAdvertisementServiceModel.setUser(userService.findByUsername(model.getUser()));
        return partAdvertisementServiceModel;
    }

    public static PartServiceModel initPartServiceModel(PartAdvertisementBindingModel model) {
        PartServiceModel partServiceModel = new PartServiceModel();
        partServiceModel.setName(model.getName());
        partServiceModel.setManufacturer(model.getManufacturer());
        partServiceModel.setCarSuitableFor(model.getCarSuitableFor());
        return partServiceModel;
    }

    public static AddressServiceModel initAddressServiceModel(AdvertisementBindingModel model) {
        AddressServiceModel addressServiceModel = new AddressServiceModel();
        addressServiceModel.setCity(model.getCity());
        addressServiceModel.setCountry(model.getCountry());
        addressServiceModel.setProvince(model.getProvince());
        return addressServiceModel;
    }

    public static void setUpBindingModel(PartAdvertisementBindingModel partAdvertisementBindingModel, PartAdvertisementServiceModel part) {
        partAdvertisementBindingModel.setName(part.getPart().getName());
        partAdvertisementBindingModel.setCarSuitableFor(part.getPart().getCarSuitableFor());
        partAdvertisementBindingModel.setManufacturer(part.getPart().getManufacturer());
        partAdvertisementBindingModel.setCountry(part.getAddress().getCountry());
        partAdvertisementBindingModel.setProvince(part.getAddress().getProvince());
        partAdvertisementBindingModel.setCity(part.getAddress().getCity());
        partAdvertisementBindingModel.setPrice(part.getPrice());
        partAdvertisementBindingModel.setDescription(part.getDescription());
    }

    public static CarAdvertisementServiceModel initCarAdvertisementServiceModel(CarAdvertisementBindingModel model, CarServiceModel carServiceModel, AddressServiceModel addressServiceModel, UserService userService) {
        CarAdvertisementServiceModel carAdvertisementServiceModel = new CarAdvertisementServiceModel();
        carAdvertisementServiceModel.setAddress(addressServiceModel);
        carAdvertisementServiceModel.setCar(carServiceModel);
        carAdvertisementServiceModel.setDescription(model.getDescription());
        carAdvertisementServiceModel.setPrice(model.getPrice());
        carAdvertisementServiceModel.setUser(userService.findByUsername(model.getUser()));
        return carAdvertisementServiceModel;
    }

    public static CarServiceModel initCarServiceModel(CarAdvertisementBindingModel model) {
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

    public static void setUpBindingModel(CarAdvertisementBindingModel bindingModel, CarAdvertisementServiceModel car) {
        bindingModel.setCarType(car.getCar().getType());
        bindingModel.setMake(car.getCar().getMake());
        bindingModel.setModel(car.getCar().getModel());
        bindingModel.setColor(car.getCar().getColor());
        bindingModel.setProductionDate(car.getCar().getProductionDate());
        bindingModel.setMileage(car.getCar().getMileage());
        bindingModel.setFuelType(car.getCar().getEngine().getFuelType());
        bindingModel.setHorsepower(car.getCar().getEngine().getHorsepower());
        bindingModel.setModification(car.getCar().getEngine().getModification());
        bindingModel.setVolume(car.getCar().getEngine().getVolume());
        bindingModel.setPrice(car.getPrice());
        bindingModel.setDescription(car.getDescription());
        bindingModel.setCountry(car.getAddress().getCountry());
        bindingModel.setCity(car.getAddress().getCity());
        bindingModel.setProvince(car.getAddress().getProvince());
    }

    public static UserProfileViewModel findUser(String id, UserService userService, ModelMapper modelMapper) {
        UserServiceModel userServiceModel = userService.findById(id);
        if (userServiceModel == null) {
            throw new IllegalArgumentException("User not found");
        }
        return modelMapper.map(userServiceModel, UserProfileViewModel.class);
    }
}
