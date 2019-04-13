package com.autohub.service.implementations;

import com.autohub.domain.entity.Address;
import com.autohub.domain.entity.Car;
import com.autohub.domain.entity.CarAdvertisement;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.repository.CarAdvertisementRepository;
import com.autohub.service.interfaces.AddressService;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.CarService;
import com.autohub.service.interfaces.EngineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarAdvertisementServiceImpl implements CarAdvertisementService {
    private final CarAdvertisementRepository carAdvertisementRepository;
    private final CarService carService;
    private final EngineService engineService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarAdvertisementServiceImpl(CarAdvertisementRepository carAdvertisementRepository, CarService carService, EngineService engineService, AddressService addressService, ModelMapper modelMapper) {
        this.carAdvertisementRepository = carAdvertisementRepository;
        this.carService = carService;
        this.engineService = engineService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarAdvertisementServiceModel save(CarAdvertisementServiceModel carAdvertisementServiceModel) {
        try {
            EngineServiceModel engine = this.engineService.save(carAdvertisementServiceModel.getCar().getEngine());
            AddressServiceModel address = this.addressService.save(carAdvertisementServiceModel.getAddress());
            CarServiceModel car = carAdvertisementServiceModel.getCar();
            car.setEngine(engine);
            car = this.carService.save(car);
            CarAdvertisement carAdvertisement = this.modelMapper.map(carAdvertisementServiceModel, CarAdvertisement.class);
            carAdvertisement.setStatus(AdvertisementStatus.PENDING);
            carAdvertisement.setCar(this.modelMapper.map(this.carService.findById(car.getId()), Car.class));
            carAdvertisement.setAddress(this.modelMapper.map(this.addressService.findById(address.getId()), Address.class));
            carAdvertisement = this.carAdvertisementRepository.save(carAdvertisement);
            return this.modelMapper.map(carAdvertisement, CarAdvertisementServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CarAdvertisementServiceModel> findAll() {
        return this.carAdvertisementRepository.findAll()
                .stream()
                .map(car -> this.modelMapper.map(car, CarAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarAdvertisementServiceModel> findAllByUserId(String id) {
        return this.carAdvertisementRepository.findAllByUserId(id)
                .stream()
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarAdvertisementServiceModel findById(String id) {
        CarAdvertisement carAdvertisement = this.carAdvertisementRepository.findById(id).orElse(null);
        return carAdvertisement == null ? null : this.modelMapper.map(carAdvertisement, CarAdvertisementServiceModel.class);
    }

    @Override
    public void changeAdvertisementStatus(String id, AdvertisementStatus status) {
        CarAdvertisement carAdvertisement = this.carAdvertisementRepository.findById(id).orElse(null);
        if (carAdvertisement != null) {
            carAdvertisement.setStatus(status);
            this.carAdvertisementRepository.save(carAdvertisement);
        }
    }

    @Override
    public void deleteById(String id) {
        this.carAdvertisementRepository.deleteById(id);
    }

    @Override
    public CarAdvertisementServiceModel update(CarAdvertisementServiceModel carAdvertisementServiceModel) {
        CarAdvertisement savedCarAdvertisement =
                this.carAdvertisementRepository.save(this.modelMapper.map(carAdvertisementServiceModel, CarAdvertisement.class));
        return savedCarAdvertisement == null ? null : this.modelMapper.map(savedCarAdvertisement, CarAdvertisementServiceModel.class);
    }
}
