package com.autohub.domain.model.binding;

import com.autohub.domain.enums.Gender;

import javax.validation.constraints.*;

public class UserEditBindingModel {
    private String username;
    private String oldPassword;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Integer age;

    public UserEditBindingModel() {
    }

    @NotEmpty
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 symbols long")
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

    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]+", message = "First name not valid")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]+", message = "Last name not valid")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email invalid")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Gender should not be null")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Min(value = 13, message = "You should be 13 or older to register")
    @Max(130)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotEmpty
    @Pattern(regexp = "[+]?[0-9]+", message = "Phone number invalid")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
