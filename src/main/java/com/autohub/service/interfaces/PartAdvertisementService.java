package com.autohub.service.interfaces;

import com.autohub.domain.model.service.PartAdvertisementServiceModel;

import java.util.List;

public interface PartAdvertisementService {
    PartAdvertisementServiceModel save(PartAdvertisementServiceModel partAdvertisementServiceModel);

    List<PartAdvertisementServiceModel> findAll();

    List<PartAdvertisementServiceModel> findAllByUserId(String id);
}
