package com.autohub.service.implementations;

import com.autohub.service.interfaces.EngineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineService engineService;
    private final ModelMapper modelMapper;

    @Autowired
    public EngineServiceImpl(EngineService engineService, ModelMapper modelMapper) {
        this.engineService = engineService;
        this.modelMapper = modelMapper;
    }
}
