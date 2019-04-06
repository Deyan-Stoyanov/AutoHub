package com.autohub.domain.model.binding;

import com.autohub.domain.annotations.BeforeToday;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

public class CarAdvertisementBindingModel extends AdvertisementBindingModel {
    private String make;
    private String model;
    private CarType carType;
    private BigDecimal volume;
    private Long horsepower;
    private FuelType fuelType;
    private String modification;
    private Date productionDate;
    private Long mileage;
    private String color;


    public CarAdvertisementBindingModel() {
    }

    @NotEmpty
    @Size(min = 2, max = 30, message = "Model should be between 2 and 30 characters long")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotEmpty
    @Size(min = 2, max = 30, message = "Model should be between 2 and 30 characters long")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull(message = "Car type should not be empty")
    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @NotNull(message = "Date should not be empty")
    @BeforeToday
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Min(value = 0, message = "Mileage should be between non-negative")
    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    @NotEmpty
    @Size(min = 2, max = 30, message = "Color should be between 2 and 30 characters long")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }

    @NotNull(message = "Fuel type should not be empty")
    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Min(value = 1, message = "Horsepower should be a positive number")
    public Long getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Long horsepower) {
        this.horsepower = horsepower;
    }

    @DecimalMin(value = "0.1", message = "Engine Volume should not be negative")
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
