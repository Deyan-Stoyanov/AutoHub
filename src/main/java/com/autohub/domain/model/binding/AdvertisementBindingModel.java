package com.autohub.domain.model.binding;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public abstract class AdvertisementBindingModel {
    private BigDecimal price;
    private String description;
    private String country;
    private String province;
    private String city;
    private String user;

    public AdvertisementBindingModel() {
    }

    @DecimalMin(value = "0.01", message = "Price should be a positive decimal number")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotEmpty
    @Size(min = 3, message = "Description should be at least 3 symbols long")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = "Country should be between 3 and 50 characters long")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = "Province should be between 3 and 50 characters long")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = "City should be between 3 and 50 characters long")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
