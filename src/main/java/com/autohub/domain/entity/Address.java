package com.autohub.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{
    private String country;
    private String province;
    private String city;
    private List<CarAdvertisement> carAdvertisements;
    private List<PartAdvertisement> partAdvertisements;

    public Address() {
    }

    public Address(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address", targetEntity = CarAdvertisement.class)
    public List<CarAdvertisement> getCarAdvertisements() {
        return carAdvertisements;
    }

    public void setCarAdvertisements(List<CarAdvertisement> carAdvertisements) {
        this.carAdvertisements = carAdvertisements;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "address", targetEntity = PartAdvertisement.class)
    public List<PartAdvertisement> getPartAdvertisements() {
        return partAdvertisements;
    }

    public void setPartAdvertisements(List<PartAdvertisement> partAdvertisements) {
        this.partAdvertisements = partAdvertisements;
    }
}
