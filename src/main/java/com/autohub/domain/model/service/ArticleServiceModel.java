package com.autohub.domain.model.service;

import com.autohub.domain.enums.ArticleType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ArticleServiceModel extends BaseServiceModel{
    private String title;
    private String content;
    private ArticleType type;
    private Date creationDate;

    public ArticleServiceModel() {
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

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
