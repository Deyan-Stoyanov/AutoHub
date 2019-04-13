package com.autohub.domain.entity;

import javax.persistence.*;
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

    @OneToOne(targetEntity = Part.class, fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
