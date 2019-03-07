package com.autohub.service.implementations;

import com.autohub.repository.EngineRepository;
import com.autohub.service.interfaces.EngineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineServiceImpl implements EngineService {
    private final EngineRepository engineRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public EngineServiceImpl(EngineRepository engineRepository, ModelMapper modelMapper) {
        this.engineRepository = engineRepository;
        this.modelMapper = modelMapper;
    }
}
