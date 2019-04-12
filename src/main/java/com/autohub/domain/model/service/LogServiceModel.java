package com.autohub.domain.model.service;

import com.autohub.domain.enums.LogType;

import java.time.LocalDateTime;
import java.util.Date;

public class LogServiceModel {
    private String id;
    private String message;
    private LocalDateTime date;
    private LogType type;

    public LogServiceModel() {
    }

    public LogServiceModel(String message, LogType type) {
        this.message = message;
        this.type = type;
        this.setDate(LocalDateTime.now());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }
}
