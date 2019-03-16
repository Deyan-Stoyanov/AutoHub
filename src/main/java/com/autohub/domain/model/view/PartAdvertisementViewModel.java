package com.autohub.domain.model.view;

import java.math.BigDecimal;

public class PartAdvertisementViewModel {

    private String id;
    private BigDecimal price;
    private String description;
    private AddressViewModel address;
    private UserProfileViewModel user;
    private PartViewModel part;

    public PartAdvertisementViewModel() {
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

    public AddressViewModel getAddress() {
        return address;
    }

    public void setAddress(AddressViewModel address) {
        this.address = address;
    }

    public UserProfileViewModel getUser() {
        return user;
    }

    public void setUser(UserProfileViewModel user) {
        this.user = user;
    }

    public PartViewModel getPart() {
        return part;
    }

    public void setPart(PartViewModel part) {
        this.part = part;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}