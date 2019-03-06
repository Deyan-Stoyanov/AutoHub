package com.autohub.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class Advertisement extends BaseEntity {
    private Car car;
    private BigDecimal price;
    private String description;
    private Address address;

    public Advertisement() {
    }

    public Advertisement(BigDecimal price, String description, Address address) {
        this.price = price;
        this.description = description;
        this.address = address;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Address.class)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
