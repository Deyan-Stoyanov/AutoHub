package com.autohub.domain.model.view;

import com.autohub.domain.enums.AdvertisementStatus;

import java.math.BigDecimal;

public class PartAdvertisementViewModel {

    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private AddressViewModel address;
    private UserProfileViewModel user;
    private PartViewModel part;
    private AdvertisementStatus status;
    private String imageFileName;

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

    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
