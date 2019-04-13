package com.autohub.service.interfaces;

import com.autohub.domain.entity.Car;
import com.autohub.domain.model.service.CarServiceModel;

public interface CarService {
    CarServiceModel save(CarServiceModel carServiceModel);

    CarServiceModel findById(String id);
}
