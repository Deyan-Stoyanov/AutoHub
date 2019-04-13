package com.autohub.service.implementations;

import com.autohub.domain.entity.Address;
import com.autohub.domain.model.service.AddressServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.service.interfaces.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressServiceModel save(AddressServiceModel addressServiceModel){
        try {
            Address address = this.modelMapper.map(addressServiceModel, Address.class);
            Address saved = this.addressRepository.save(address);
            return this.modelMapper.map(saved, AddressServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AddressServiceModel findById(String id){
        Address address = this.addressRepository.findById(id).orElse(null);
        return address == null ? null : this.modelMapper.map(address, AddressServiceModel.class);
    }
}
