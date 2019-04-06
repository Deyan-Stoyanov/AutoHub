package com.autohub.service.implementations;

import com.autohub.domain.entity.Article;
import com.autohub.domain.enums.ArticleType;
import com.autohub.domain.model.service.ArticleServiceModel;
import com.autohub.repository.ArticleRepository;
import com.autohub.service.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArticleServiceModel save(ArticleServiceModel articleServiceModel) {
        try {
            Article article = this.articleRepository
                    .save(this.modelMapper.map(articleServiceModel, Article.class));
            return this.modelMapper.map(article, ArticleServiceModel.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ArticleServiceModel> findAllByType(ArticleType type) {
        return this.articleRepository.findAllByType(type)
                .stream()
                .map(article -> this.modelMapper.map(article, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        this.articleRepository.deleteById(id);
    }

    @Override
    public ArticleServiceModel findById(String id) {
        Article article = this.articleRepository.findById(id).orElse(null);
        return article == null ? null : this.modelMapper.map(article, ArticleServiceModel.class);
    }

    @Override
    public ArticleServiceModel update(ArticleServiceModel articleServiceModel) {
        Article article = this.modelMapper.map(articleServiceModel, Article.class);
        article = this.articleRepository.save(article);
        return article == null ? null : this.modelMapper.map(article, ArticleServiceModel.class);
    }
}
