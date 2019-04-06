package com.autohub.domain.model.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PartAdvertisementBindingModel extends AdvertisementBindingModel {
    private String name;
    private String manufacturer;
    private String carSuitableFor;

    public PartAdvertisementBindingModel() {
    }

    @NotEmpty
    @Size(min = 2, max = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    @Size(min = 3, max = 30)
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCarSuitableFor() {
        return carSuitableFor;
    }

    public void setCarSuitableFor(String carSuitableFor) {
        this.carSuitableFor = carSuitableFor;
    }

}