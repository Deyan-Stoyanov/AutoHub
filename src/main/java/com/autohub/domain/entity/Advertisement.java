package com.autohub.domain.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class Advertisement extends BaseEntity {
    private BigDecimal price;
    private String description;
    private Address address;
    private User user;

    public Advertisement() {
    }

    public Advertisement(BigDecimal price, String description, Address address, User user) {
        this.price = price;
        this.description = description;
        this.address = address;
        this.user = user;
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

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
