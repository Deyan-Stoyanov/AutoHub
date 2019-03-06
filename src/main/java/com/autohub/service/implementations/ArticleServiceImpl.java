package com.autohub.service.implementations;

import com.autohub.repository.ArticleRepository;
import com.autohub.service.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository addressRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }
}
