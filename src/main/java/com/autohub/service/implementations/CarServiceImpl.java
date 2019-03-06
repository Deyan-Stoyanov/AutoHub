package com.autohub.service.implementations;

import com.autohub.repository.CarRepository;
import com.autohub.service.interfaces.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }
}
