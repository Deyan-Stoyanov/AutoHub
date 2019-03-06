package com.autohub.domain.entity;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "part_advertisements")
public class PartAdvertisment extends Advertisement {
    private Part part;

    public PartAdvertisment() {
    }

    public PartAdvertisment(BigDecimal price, String description, Address address, Part part) {
        super(price, description, address);
        this.part = part;
    }

    @OneToOne(mappedBy = "id", targetEntity = Part.class)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }
}
