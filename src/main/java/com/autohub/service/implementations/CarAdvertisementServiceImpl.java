package com.autohub.service.implementations;

import com.autohub.domain.entity.CarAdvertisement;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import com.autohub.repository.CarAdvertisementRepository;
import com.autohub.service.interfaces.CarAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarAdvertisementServiceImpl implements CarAdvertisementService {
    private final CarAdvertisementRepository carAdvertismentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarAdvertisementServiceImpl(CarAdvertisementRepository carAdvertismentRepository, ModelMapper modelMapper) {
        this.carAdvertismentRepository = carAdvertismentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarAdvertisementServiceModel> findAll(){
        return this.carAdvertismentRepository.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, CarAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }
}
