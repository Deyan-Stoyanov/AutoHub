package com.autohub.domain.entity;

import com.autohub.domain.enums.AdvertisementStatus;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class Advertisement extends BaseEntity {
    private BigDecimal price;
    private String description;
    private Address address;
    private User user;
    private AdvertisementStatus status;

    public Advertisement() {
        this.setStatus(AdvertisementStatus.PENDING);
    }

    public Advertisement(BigDecimal price, String description, Address address, User user) {
        this.price = price;
        this.description = description;
        this.address = address;
        this.user = user;
        this.setStatus(AdvertisementStatus.PENDING);
    }

    @Column(name = "price", nullable = false)
    @DecimalMin("0.00")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
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

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public AdvertisementStatus getStatus() {
        return status;
    }

    public void setStatus(AdvertisementStatus status) {
        this.status = status;
    }
}
