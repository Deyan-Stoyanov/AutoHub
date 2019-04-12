package com.autohub.service.implementations;

import com.autohub.domain.entity.Log;
import com.autohub.domain.model.service.LogServiceModel;
import com.autohub.repository.LogRepository;
import com.autohub.service.interfaces.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel save(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        try {
            log = this.logRepository.save(log);
        } catch (Exception e){
            return null;
        }
        return this.modelMapper.map(log, LogServiceModel.class);
    }

    @Override
    public LogServiceModel findById(String id) {
        Log log = this.logRepository.findById(id).orElse(null);
        return log == null ? null : this.modelMapper.map(log, LogServiceModel.class);
    }

    @Override
    public List<LogServiceModel> findAll() {
        return this.logRepository.findAll()
                .stream()
                .map(log -> this.modelMapper.map(log, LogServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        this.logRepository.deleteById(id);
    }
}
