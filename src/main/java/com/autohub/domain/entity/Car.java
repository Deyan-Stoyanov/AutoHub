package com.autohub.domain.entity;

import com.autohub.domain.enums.CarType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "car")
public class Car extends BaseEntity{
    private String make;
    private String model;
    private CarType type;
    private Engine engine;
    private Date productionDate;
    private Long mileage;
    private String color;

    public Car() {
    }

    public Car(String make, String model, CarType type, Engine engine, Date productionDate, Long mileage, String color) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.engine = engine;
        this.productionDate = productionDate;
        this.mileage = mileage;
        this.color = color;
    }

    @Column(name = "make", nullable = false)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "model", nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    @ManyToOne(targetEntity = Engine.class, cascade = CascadeType.MERGE)
    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Column(name = "production_date")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Column(name = "mileage")
    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
