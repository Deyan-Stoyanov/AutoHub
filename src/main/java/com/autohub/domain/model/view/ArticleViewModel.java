package com.autohub.domain.model.view;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ArticleViewModel {
    private String id;
    private String title;
    private String content;
    private Date creationDate;

    public ArticleViewModel() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
