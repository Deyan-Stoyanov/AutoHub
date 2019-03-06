package com.autohub.domain.entity;

import com.autohub.domain.enums.ArticleType;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {
    private String title;
    private String content;
    private ArticleType type;
    private boolean isApproved;

    public Article() {
    }

    public Article(String title, String content, ArticleType type, boolean isApproved) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.isApproved = isApproved;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "title", nullable = false, columnDefinition = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }

    @Column(name = "title", nullable = false)
    @ColumnDefault("false")
    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
