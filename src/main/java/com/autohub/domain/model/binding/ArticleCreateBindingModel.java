package com.autohub.domain.model.binding;

import com.autohub.domain.enums.ArticleType;

import java.util.Date;

public class ArticleCreateBindingModel {
    private String title;
    private String content;
    private ArticleType type;
    private Date creationDate;

    public ArticleCreateBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }
}
