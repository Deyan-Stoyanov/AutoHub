package com.autohub.repository;

import com.autohub.domain.entity.Article;
import com.autohub.domain.enums.ArticleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, String> {
    List<Article> findAllByType(ArticleType type);
}
