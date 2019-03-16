package com.autohub.domain.model.view;

import com.autohub.domain.enums.FuelType;

import java.math.BigDecimal;

public class EngineViewModel {
    private BigDecimal volume;
    private Long horsepower;
    private FuelType fuelType;
    private String modification;

    public EngineViewModel() {
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public Long getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Long horsepower) {
        this.horsepower = horsepower;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getModification() {
        return modification;
    }

    public void setModification(String modification) {
        this.modification = modification;
    }
}
