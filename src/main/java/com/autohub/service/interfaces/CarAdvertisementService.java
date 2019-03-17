package com.autohub.service.interfaces;

import com.autohub.domain.model.service.CarAdvertisementServiceModel;

import java.util.List;

public interface CarAdvertisementService {
    CarAdvertisementServiceModel save(CarAdvertisementServiceModel carAdvertisementServiceModel);

    List<CarAdvertisementServiceModel> findAll();

    List<CarAdvertisementServiceModel> findAllByUserId(String id);
}
