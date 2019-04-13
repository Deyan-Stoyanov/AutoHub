package com.autohub.service.implementations;

import com.autohub.domain.entity.Car;
import com.autohub.domain.entity.Engine;
import com.autohub.domain.model.service.CarServiceModel;
import com.autohub.repository.CarRepository;
import com.autohub.service.interfaces.CarService;
import com.autohub.service.interfaces.EngineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final EngineService engineService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, EngineService engineService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.engineService = engineService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarServiceModel save(CarServiceModel carServiceModel) {
        try {
            Car car = this.modelMapper.map(carServiceModel, Car.class);
            car.setEngine(this.modelMapper.map(engineService.findById(car.getEngine().getId()), Engine.class));
            Car saved = this.carRepository.save(car);
            return this.modelMapper.map(saved, CarServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CarServiceModel findById(String id) {
        Car car = this.carRepository.findById(id).orElse(null);
        return car == null ? null : this.modelMapper.map(car, CarServiceModel.class);
    }
}
