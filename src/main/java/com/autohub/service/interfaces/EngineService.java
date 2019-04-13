package com.autohub.service.interfaces;

import com.autohub.domain.model.service.EngineServiceModel;

public interface EngineService {
    EngineServiceModel save(EngineServiceModel engineServiceModel);

    EngineServiceModel findById(String id);
}
