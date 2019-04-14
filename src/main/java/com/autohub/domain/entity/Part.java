package com.autohub.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private String manufacturer;
    private String carSuitableFor;
    private PartAdvertisement partAdvertisement;

    public Part() {
    }

    public Part(String name, String producer, String carSuitableFor) {
        this.name = name;
        this.manufacturer = producer;
        this.carSuitableFor = carSuitableFor;
    }

    @Size(min = 3, max = 50)
    @NotNull
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "car_suitable_for")
    public String getCarSuitableFor() {
        return carSuitableFor;
    }

    public void setCarSuitableFor(String carSuitableFor) {
        this.carSuitableFor = carSuitableFor;
    }

    @NotNull
    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @OneToOne(mappedBy = "part", targetEntity = PartAdvertisement.class)
    public PartAdvertisement getPartAdvertisement() {
        return partAdvertisement;
    }

    public void setPartAdvertisement(PartAdvertisement partAdvertisement) {
        this.partAdvertisement = partAdvertisement;
    }
}
