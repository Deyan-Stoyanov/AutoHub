package com.autohub.service.implementations;

import com.autohub.domain.entity.Address;
import com.autohub.domain.entity.Car;
import com.autohub.domain.entity.CarAdvertisement;
import com.autohub.domain.entity.Engine;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.domain.model.service.EngineServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.repository.CarAdvertisementRepository;
import com.autohub.repository.CarRepository;
import com.autohub.repository.EngineRepository;
import com.autohub.service.interfaces.CarAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarAdvertisementServiceImpl implements CarAdvertisementService {
    private final CarAdvertisementRepository carAdvertisementRepository;
    private final CarRepository carRepository;
    private final EngineRepository engineRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarAdvertisementServiceImpl(CarAdvertisementRepository carAdvertisementRepository, CarRepository carRepository, EngineRepository engineRepository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.carAdvertisementRepository = carAdvertisementRepository;
        this.carRepository = carRepository;
        this.engineRepository = engineRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarAdvertisementServiceModel save(CarAdvertisementServiceModel carAdvertisementServiceModel) {
        try {
            Engine engine = this.engineRepository.save(this.modelMapper.map(carAdvertisementServiceModel.getCar().getEngine(), Engine.class));
            Address address = this.addressRepository.save(this.modelMapper.map(carAdvertisementServiceModel.getAddress(), Address.class));
            CarServiceModel carServiceModel = carAdvertisementServiceModel.getCar();
            carServiceModel.setEngine(this.modelMapper.map(engine, EngineServiceModel.class));
            Car car = this.carRepository.save(this.modelMapper.map(carServiceModel, Car.class));
            CarAdvertisement carAdvertisement = this.modelMapper.map(carAdvertisementServiceModel, CarAdvertisement.class);
            carAdvertisement.setStatus(AdvertisementStatus.PENDING);
            carAdvertisement.setCar(car);
            carAdvertisement.setAddress(address);
            this.carAdvertisementRepository.save(carAdvertisement);
            return this.modelMapper.map(carAdvertisement, CarAdvertisementServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CarAdvertisementServiceModel> findAll() {
        return this.carAdvertisementRepository.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementServiceModel.class))
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
