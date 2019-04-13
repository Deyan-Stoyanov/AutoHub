package com.autohub.service.implementations;

import com.autohub.domain.entity.Address;
import com.autohub.domain.entity.Part;
import com.autohub.domain.entity.PartAdvertisement;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.PartAdvertisementServiceModel;
import com.autohub.domain.model.service.PartServiceModel;
import com.autohub.repository.PartAdvertisementRepository;
import com.autohub.service.interfaces.AddressService;
import com.autohub.service.interfaces.PartAdvertisementService;
import com.autohub.service.interfaces.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartAdvertisementServiceImpl implements PartAdvertisementService {
    private final PartAdvertisementRepository partAdvertisementRepository;
    private final PartService partService;
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @Autowired
    public PartAdvertisementServiceImpl(PartAdvertisementRepository partAdvertisementRepository, PartService partService, AddressService addressService, ModelMapper modelMapper) {
        this.partAdvertisementRepository = partAdvertisementRepository;
        this.partService = partService;
        this.addressService = addressService;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartAdvertisementServiceModel save(PartAdvertisementServiceModel partAdvertisementServiceModel) {
        try {
            Part part = this.modelMapper
                    .map(this.partService.save(partAdvertisementServiceModel.getPart()), Part.class);
            Address address = this.modelMapper
                    .map(this.addressService.save(partAdvertisementServiceModel.getAddress()), Address.class);
            PartAdvertisement partAdvertisement = this.modelMapper.map(partAdvertisementServiceModel, PartAdvertisement.class);
            partAdvertisement.setPart(part);
            partAdvertisement.setAddress(address);
            partAdvertisement.setStatus(AdvertisementStatus.PENDING);
            this.partAdvertisementRepository.save(partAdvertisement);
            return this.modelMapper.map(partAdvertisement, PartAdvertisementServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PartAdvertisementServiceModel> findAll() {
        return this.partAdvertisementRepository.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartAdvertisementServiceModel> findAllByUserId(String id) {
        return this.partAdvertisementRepository.findAllByUserId(id)
                .stream()
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PartAdvertisementServiceModel findById(String id) {
        PartAdvertisement partAdvertisement = this.partAdvertisementRepository.findById(id).orElse(null);
        return partAdvertisement == null ? null : this.modelMapper.map(partAdvertisement, PartAdvertisementServiceModel.class);
    }

    @Override
    public void changeAdvertisementStatus(String id, AdvertisementStatus status) {
        PartAdvertisement partAdvertisement = this.partAdvertisementRepository.findById(id).orElse(null);
        if (partAdvertisement != null) {
            partAdvertisement.setStatus(status);
            this.partAdvertisementRepository.save(partAdvertisement);
        }
    }

    @Override
    public void deleteById(String id) {
        this.partAdvertisementRepository.deleteById(id);
    }
}
