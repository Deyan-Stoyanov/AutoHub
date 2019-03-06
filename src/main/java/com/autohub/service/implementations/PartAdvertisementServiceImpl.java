package com.autohub.service.implementations;

import com.autohub.repository.PartAdvertismentRepository;
import com.autohub.service.interfaces.PartAdvertisementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartAdvertisementServiceImpl implements PartAdvertisementService {
    private final PartAdvertismentRepository partAdvertismentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartAdvertisementServiceImpl(PartAdvertismentRepository partAdvertismentRepository, ModelMapper modelMapper) {
        this.partAdvertismentRepository = partAdvertismentRepository;
        this.modelMapper = modelMapper;
    }
}
