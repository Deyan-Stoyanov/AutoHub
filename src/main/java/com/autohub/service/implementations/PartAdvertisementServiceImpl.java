package com.autohub.service.implementations;

import com.autohub.domain.entity.Address;
import com.autohub.domain.entity.Part;
import com.autohub.domain.entity.PartAdvertisement;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.model.service.PartAdvertisementServiceModel;
import com.autohub.repository.AddressRepository;
import com.autohub.repository.PartAdvertisementRepository;
import com.autohub.repository.PartRepository;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartAdvertisementServiceImpl implements PartAdvertisementService {
    private final PartAdvertisementRepository partAdvertisementRepository;
    private final PartRepository partRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartAdvertisementServiceImpl(PartAdvertisementRepository partAdvertisementRepository, PartRepository partRepository, AddressRepository addressRepository, ModelMapper modelMapper) {
        this.partAdvertisementRepository = partAdvertisementRepository;
        this.partRepository = partRepository;
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartAdvertisementServiceModel save(PartAdvertisementServiceModel partAdvertisementServiceModel) {
        try {
            Part part = this.partRepository.save(this.modelMapper.map(partAdvertisementServiceModel.getPart(), Part.class));
            Address address = this.addressRepository.save(this.modelMapper.map(partAdvertisementServiceModel.getAddress(), Address.class));
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
}
