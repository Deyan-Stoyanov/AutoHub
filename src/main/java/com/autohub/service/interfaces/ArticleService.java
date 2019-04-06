package com.autohub.service.interfaces;

import com.autohub.domain.enums.ArticleType;
import com.autohub.domain.model.service.ArticleServiceModel;

import java.util.List;

public interface ArticleService {
    ArticleServiceModel save(ArticleServiceModel articleServiceModel);

    List<ArticleServiceModel> findAllByType(ArticleType type);

    void deleteById(String id);

    ArticleServiceModel findById(String id);

    ArticleServiceModel update(ArticleServiceModel articleServiceModel);
}
