package com.autohub.domain.entity;

import com.autohub.domain.enums.Gender;
import com.autohub.domain.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Integer age;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Collection<? extends UserRole> authorities;
    private List<CarAdvertisement> carAdvertisements;
    private List<PartAdvertisement> partAdvertisements;

    public User() {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setAuthorities(Collection<? extends UserRole> authorities) {
        this.authorities = authorities;
    }

    @Size(min = 3, max = 20)
    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(255)")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Size(min = 2, max = 40)
    @Pattern(regexp = "[A-Z][a-z]+", message = "First name not valid")
    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 2, max = 40)
    @Pattern(regexp = "[A-Z][a-z]+", message = "Last name not valid")
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Size(min = 2, max = 40)
    @Email
    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Min(0)
    @Max(199)
    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    @Size(min = 4, max = 20)
    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = true;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = true;
    }
}
