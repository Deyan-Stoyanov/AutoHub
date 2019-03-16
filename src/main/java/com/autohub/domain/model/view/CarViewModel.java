package com.autohub.domain.model.view;

import com.autohub.domain.enums.CarType;

import java.util.Date;

public class CarViewModel {
    private String make;
    private String model;
    private CarType type;
    private EngineViewModel engine;
    private Date productionDate;
    private Long mileage;
    private String color;

    public CarViewModel() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public EngineViewModel getEngine() {
        return engine;
    }

    public void setEngine(EngineViewModel engine) {
        this.engine = engine;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
