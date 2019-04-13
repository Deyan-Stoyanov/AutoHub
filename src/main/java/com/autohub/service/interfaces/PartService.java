package com.autohub.service.interfaces;

import com.autohub.domain.model.service.PartServiceModel;

public interface PartService {
    PartServiceModel save(PartServiceModel partServiceModel);

    PartServiceModel findById(String id);
}
