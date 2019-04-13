package com.autohub.service.interfaces;

import com.autohub.domain.model.service.AddressServiceModel;

public interface AddressService {
    AddressServiceModel save(AddressServiceModel addressServiceModel);

    AddressServiceModel findById(String id);
}
