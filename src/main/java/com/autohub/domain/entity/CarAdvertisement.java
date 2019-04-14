package com.autohub.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "car_advertisements")
public class CarAdvertisement extends Advertisement {
    private Car car;

    public CarAdvertisement() {
    }

    public CarAdvertisement(BigDecimal price, String description, Address address, User user, Car car) {
        super(price, description, address, user);
        this.car = car;
    }

    @OneToOne(targetEntity = Car.class)
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
