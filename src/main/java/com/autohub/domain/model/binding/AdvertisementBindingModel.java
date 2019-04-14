package com.autohub.domain.model.binding;

import com.autohub.util.Const;

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

    @DecimalMin(value = "0.01", message = Const.PRICE_VALIDATION_MESSAGE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotEmpty
    @Size(min = 3, message = Const.DESCRIPTION_VALIDATION_MESSAGE)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = Const.COUNTRY_VALIDATION_MESSAGE)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = Const.PROVINCE_VALIDATION_MESSAGE)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @NotEmpty
    @Size(min = 3, max = 50, message = Const.CITY_VALIDATION_MESSAGE)
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
