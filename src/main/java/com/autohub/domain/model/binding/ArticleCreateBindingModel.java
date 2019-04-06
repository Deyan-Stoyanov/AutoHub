package com.autohub.domain.model.binding;

import com.autohub.domain.enums.ArticleType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class ArticleCreateBindingModel {
    private String title;
    private String content;
    private ArticleType type;
    private Date creationDate;

    public ArticleCreateBindingModel() {
    }

    @NotEmpty
    @Size(min = 4, max = 30, message = "Title should not be empty")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty
    @Size(min = 4, message = "Content should be at least 3 symbols long")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull(message = "Type should not be empty")
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
