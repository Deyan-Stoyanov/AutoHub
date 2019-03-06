package com.autohub.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private String carSuitableFor;

    public Part() {
    }

    public Part(String name, String carSuitableFor) {
        this.name = name;
        this.carSuitableFor = carSuitableFor;
    }

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
}
