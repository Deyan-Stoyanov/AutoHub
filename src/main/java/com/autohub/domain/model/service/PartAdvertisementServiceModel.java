package com.autohub.domain.model.service;

import com.autohub.domain.enums.AdvertisementStatus;

import java.math.BigDecimal;

public class PartAdvertisementServiceModel extends BaseServiceModel{
    private BigDecimal price;
    private String description;
    private AddressServiceModel address;
    private UserServiceModel user;
    private PartServiceModel part;
    private AdvertisementStatus status;

    public PartAdvertisementServiceModel() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressServiceModel getAddress() {
        return address;
    }

    public void setAddress(AddressServiceModel address) {
        this.address = address;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public PartServiceModel getPart() {
        return part;
    }

    public void setPart(PartServiceModel part) {
        this.part = part;
    }

    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }
}
