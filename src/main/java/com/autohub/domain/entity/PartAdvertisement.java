package com.autohub.domain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "part_advertisements")
public class PartAdvertisement extends Advertisement {
    private Part part;

    public PartAdvertisement() {
    }

    public PartAdvertisement(BigDecimal price, String description, Address address, User user, Part part) {
        super(price, description, address, user);
        this.part = part;
    }

    @OneToOne(targetEntity = Part.class)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
