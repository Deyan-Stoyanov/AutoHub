package com.autohub.domain.entity;

import com.autohub.domain.enums.ArticleType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    private String title;
    private String content;
    private ArticleType type;
    private Date creationDate;

    public Article() {
    }

    public Article(String title, String content, ArticleType type, Date creationDate) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.creationDate = creationDate;
    }

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 3)
    @Column(name = "content", nullable = false, columnDefinition = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
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
