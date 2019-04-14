package com.autohub.domain.model.binding;

import com.autohub.domain.enums.Gender;
import com.autohub.util.Const;

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
    @Size(min = 3, max = 20, message = Const.USERNAME_VALIDATION_MESSAGE)
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
    @Pattern(regexp = "[A-Z][a-z]+", message = Const.FIRST_NAME_VALIDATION_MESSAGE)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotEmpty
    @Pattern(regexp = "[A-Z][a-z]+", message = Const.LAST_NAME_VALIDATION_MESSAGE)
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

    @NotNull(message = Const.GENDER_VALIDATION_MESSAGE)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Min(value = 13, message = Const.AGE_VALIDATION_MESSAGE)
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
    @Pattern(regexp = "[+]?[0-9]+", message = Const.PHONE_NUMBER_VALIDATION_MESSAGE)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
