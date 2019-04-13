package com.autohub.domain.model.service;

public abstract class EntityContainingImageServiceModel extends BaseServiceModel {
    private String imageFileName;

    public EntityContainingImageServiceModel() {

    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
