package com.autohub.domain.entity;

import com.autohub.domain.enums.FuelType;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "engines")
public class Engine extends BaseEntity{
    private BigDecimal volume;
    private Long horsepower;
    private FuelType fuelType;
    private String modification;

    public Engine() {
    }

    public Engine(BigDecimal volume, Long horsepower, FuelType fuelType, String modification) {
        this.volume = volume;
        this.horsepower = horsepower;
        this.fuelType = fuelType;
        this.modification = modification;
    }

    @Column(name = "volume", nullable = false)
    @DecimalMin("0.1")
    @DecimalMax("20.0")
    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    @Min(1)
    @Column(name = "horsepower", nullable = false)
    public Long getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Long horsepower) {
        this.horsepower = horsepower;
    }

    @NotNull
    @Column(name = "fuel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    @Column(name = "modification", nullable = false)
    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }
}
