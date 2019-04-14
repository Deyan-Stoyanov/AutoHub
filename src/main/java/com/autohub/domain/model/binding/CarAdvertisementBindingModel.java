package com.autohub.domain.model.binding;

import com.autohub.domain.annotations.BeforeToday;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.util.Const;
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
    @Size(min = 2, max = 30, message = Const.CAR_MAKE_VALIDATION_MESSAGE)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotEmpty
    @Size(min = 2, max = 30, message = Const.CAR_MODEL_VALIDATION_MESSAGE)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @NotNull(message = Const.CAR_TYPE_VALIDATION_MESSAGE)
    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @NotNull(message = Const.DATE_VALIDATION_MESSAGE)
    @BeforeToday
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    @Min(value = 0, message = Const.CAR_MILEAGE_VALIDATION_MESSAGE)
    public Long getMileage() {
        return mileage;
    }

    public void setMileage(Long mileage) {
        this.mileage = mileage;
    }

    @NotEmpty
    @Size(min = 2, max = 30, message = Const.CAR_COLOR_VALIDATION_MESSAGE)
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

    @NotNull(message = Const.CAR_FUEL_TYPE_VALIDATION_MESSAGE)
    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Min(value = 1, message = Const.CAR_HP_VALIDATION_MESSAGE)
    public Long getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Long horsepower) {
        this.horsepower = horsepower;
    }

    @DecimalMin(value = "0.1", message = Const.CAR_VOLUME_VALIDATION_MESSAGE)
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }
}
