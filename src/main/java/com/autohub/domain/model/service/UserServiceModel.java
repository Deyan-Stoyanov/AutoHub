package com.autohub.domain.model.service;

import com.autohub.domain.enums.Gender;
import com.autohub.domain.enums.Role;

import java.util.List;
import java.util.Set;

public class UserServiceModel extends EntityContainingImageServiceModel{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Integer age;
    private Set<UserRoleServiceModel> authorities;
    private List<CarAdvertisementServiceModel> carAdvertisements;
    private List<PartAdvertisementServiceModel> partAdvertisements;

    public UserServiceModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Set<UserRoleServiceModel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserRoleServiceModel> authorities) {
        this.authorities = authorities;
    }

    public List<CarAdvertisementServiceModel> getCarAdvertisements() {
        return carAdvertisements;
    }

    public void setCarAdvertisements(List<CarAdvertisementServiceModel> carAdvertisements) {
        this.carAdvertisements = carAdvertisements;
    }

    public List<PartAdvertisementServiceModel> getPartAdvertisements() {
        return partAdvertisements;
    }

    public void setPartAdvertisements(List<PartAdvertisementServiceModel> partAdvertisements) {
        this.partAdvertisements = partAdvertisements;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
