package com.autohub.service.interfaces;

import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.PartAdvertisementServiceModel;

import java.util.List;

public interface PartAdvertisementService {
    PartAdvertisementServiceModel save(PartAdvertisementServiceModel partAdvertisementServiceModel);

    List<PartAdvertisementServiceModel> findAll();

    List<PartAdvertisementServiceModel> findAllByUserId(String id);

    PartAdvertisementServiceModel findById(String id);

    void changeAdvertisementStatus(String id, AdvertisementStatus declined);

    void deleteById(String id);
}
