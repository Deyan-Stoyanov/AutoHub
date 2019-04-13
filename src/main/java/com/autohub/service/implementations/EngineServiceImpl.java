package com.autohub.service.implementations;

import com.autohub.domain.entity.Engine;
import com.autohub.domain.model.service.EngineServiceModel;
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

    @Override
    public EngineServiceModel save(EngineServiceModel engineServiceModel){
        try {
            Engine engine = this.modelMapper.map(engineServiceModel, Engine.class);
            Engine saved = this.engineRepository.save(engine);
            return this.modelMapper.map(saved, EngineServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EngineServiceModel findById(String id){
        Engine engine = this.engineRepository.findById(id).orElse(null);
        return engine == null ? null : this.modelMapper.map(engine, EngineServiceModel.class);
    }
}
