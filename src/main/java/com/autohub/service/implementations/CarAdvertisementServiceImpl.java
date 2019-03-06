package com.autohub.service.implementations;

import com.autohub.repository.CarAdvertismentRepository;
import com.autohub.service.interfaces.CarAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarAdvertisementServiceImpl implements CarAdvertisementService {
    private final CarAdvertismentRepository carAdvertismentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarAdvertisementServiceImpl(CarAdvertismentRepository carAdvertismentRepository, ModelMapper modelMapper) {
        this.carAdvertismentRepository = carAdvertismentRepository;
        this.modelMapper = modelMapper;
    }
}
