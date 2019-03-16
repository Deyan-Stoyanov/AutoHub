package com.autohub.service.interfaces;

import com.autohub.domain.model.service.CarAdvertisementServiceModel;

import java.util.List;

public interface CarAdvertisementService {
    List<CarAdvertisementServiceModel> findAll();
}
