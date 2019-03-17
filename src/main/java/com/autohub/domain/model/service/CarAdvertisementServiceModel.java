package com.autohub.domain.model.service;

import com.autohub.domain.enums.AdvertisementStatus;

import java.math.BigDecimal;

public class CarAdvertisementServiceModel extends BaseServiceModel{
    private BigDecimal price;
    private String description;
    private AddressServiceModel address;
    private UserServiceModel user;
    private CarServiceModel car;
    private AdvertisementStatus status;

    public CarAdvertisementServiceModel() {
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

    public CarServiceModel getCar() {
        return car;
    }

    public void setCar(CarServiceModel car) {
        this.car = car;
    }

    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }
}
