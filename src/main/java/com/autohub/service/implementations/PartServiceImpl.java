package com.autohub.service.implementations;

import com.autohub.domain.entity.Part;
import com.autohub.domain.model.service.PartServiceModel;
import com.autohub.repository.PartRepository;
import com.autohub.service.interfaces.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PartServiceModel save(PartServiceModel partServiceModel) {
        try {
            Part part = this.modelMapper.map(partServiceModel, Part.class);
            Part saved = this.partRepository.save(part);
            return this.modelMapper.map(saved, PartServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PartServiceModel findById(String id) {
        Part part = this.partRepository.findById(id).orElse(null);
        return part == null ? null : this.modelMapper.map(part, PartServiceModel.class);
    }
}
