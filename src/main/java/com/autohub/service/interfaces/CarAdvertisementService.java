package com.autohub.service.interfaces;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.CarAdvertisementServiceModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CarAdvertisementService {
    CarAdvertisementServiceModel save(CarAdvertisementServiceModel carAdvertisementServiceModel);

    List<CarAdvertisementServiceModel> findAll();

    List<CarAdvertisementServiceModel> findAllByUserId(String id);

    CarAdvertisementServiceModel findById(String id);

    void changeAdvertisementStatus(String id, AdvertisementStatus declined);

    void deleteById(String id);

    CarAdvertisementServiceModel update(CarAdvertisementServiceModel carAdvertisementServiceModel);
}
