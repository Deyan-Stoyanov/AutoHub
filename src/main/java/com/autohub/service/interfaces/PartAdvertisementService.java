package com.autohub.service.interfaces;

import com.autohub.domain.model.service.PartAdvertisementServiceModel;

import java.util.List;

public interface PartAdvertisementService {
    List<PartAdvertisementServiceModel> findAll();
}
