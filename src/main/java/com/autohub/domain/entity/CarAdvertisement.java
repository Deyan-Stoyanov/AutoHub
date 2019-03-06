package com.autohub.domain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "car_advertisements")
public class CarAdvertisement extends Advertisement {
    private Car car;

    public CarAdvertisement() {
    }

    public CarAdvertisement(BigDecimal price, String description, Address address, Car car) {
        super(price, description, address);
        this.car = car;
    }

    @OneToOne(targetEntity = Car.class, mappedBy = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
