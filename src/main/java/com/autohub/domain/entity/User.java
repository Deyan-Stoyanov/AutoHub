package com.autohub.domain.entity;

import com.autohub.domain.enums.Gender;
import com.autohub.domain.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Integer age;
    private Role role;
    private List<CarAdvertisement> carAdvertisements;
    private List<PartAdvertisement> partAdvertisements;

    public User() {
    }

    public User(String username, String password, String firstName, String lastName, String email, String phoneNumber, Gender gender, Integer age, Role role, List<CarAdvertisement> carAdvertisements, List<PartAdvertisement> partAdvertisements) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.age = age;
        this.role = role;
        this.carAdvertisements = carAdvertisements;
        this.partAdvertisements = partAdvertisements;
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @OneToMany(targetEntity = CarAdvertisement.class, mappedBy = "user", cascade = CascadeType.MERGE)
    public List<CarAdvertisement> getCarAdvertisements() {
        return carAdvertisements;
    }

    public void setCarAdvertisements(List<CarAdvertisement> carAdvertisements) {
        this.carAdvertisements = carAdvertisements;
    }

    @OneToMany(targetEntity = PartAdvertisement.class, mappedBy = "user", cascade = CascadeType.MERGE)
    public List<PartAdvertisement> getPartAdvertisements() {
        return partAdvertisements;
    }

    public void setPartAdvertisements(List<PartAdvertisement> partAdvertisements) {
        this.partAdvertisements = partAdvertisements;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
