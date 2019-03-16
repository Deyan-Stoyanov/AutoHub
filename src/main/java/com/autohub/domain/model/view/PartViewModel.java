package com.autohub.domain.model.view;

public class PartViewModel {
    private String name;
    private String manufacturer;
    private String carSuitableFor;

    public PartViewModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

