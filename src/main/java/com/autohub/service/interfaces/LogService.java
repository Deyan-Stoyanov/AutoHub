package com.autohub.service.interfaces;

import com.autohub.domain.model.service.LogServiceModel;

import java.util.List;

public interface LogService {
    LogServiceModel save(LogServiceModel logServiceModel);

    LogServiceModel findById(String id);

    List<LogServiceModel> findAll();

    void deleteById(String id);
}
