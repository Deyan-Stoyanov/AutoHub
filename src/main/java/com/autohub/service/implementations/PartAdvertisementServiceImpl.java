package com.autohub.service.implementations;

import com.autohub.domain.model.service.PartAdvertisementServiceModel;
import com.autohub.repository.PartAdvertismentRepository;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartAdvertisementServiceImpl implements PartAdvertisementService {
    private final PartAdvertismentRepository partAdvertismentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartAdvertisementServiceImpl(PartAdvertismentRepository partAdvertismentRepository, ModelMapper modelMapper) {
        this.partAdvertismentRepository = partAdvertismentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<PartAdvertisementServiceModel> findAll() {
        return this.partAdvertismentRepository.findAll()
                .stream()
                .map(advert -> this.modelMapper.map(advert, PartAdvertisementServiceModel.class))
                .collect(Collectors.toList());
    }
}
