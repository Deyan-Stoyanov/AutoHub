package com.autohub.domain.model.binding;

import com.autohub.domain.enums.ArticleType;
import com.autohub.util.Const;

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
        this.setCreationDate();
    }

    @NotEmpty
    @Size(min = 4, max = 255, message = Const.ARTICLE_TITLE_VALIDATION_MESSAGE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotEmpty
    @Size(min = 4, message = Const.ARTICLE_CONTENT_VALIDATION_MESSAGE)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull(message = Const.ARTICLE_TYPE_VALIDATION_MESSAGE)
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
