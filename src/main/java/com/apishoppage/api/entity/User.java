package com.apishoppage.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;



@Entity
@Table(name = "Users")
public class User {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "UserName")
    @NonNull
    @NotBlank
    private String userName;

    @Column(name = "Password")
    @NonNull
    @NotBlank
    private String password;

    @Column(unique = true, nullable = false, name = "Email")
    @Email
    private String email;

    @Column(nullable = false, name = "Roles")
    private String roles;

    public User(@JsonProperty String userName,
                @JsonProperty String password,
                @JsonProperty String email,
                @JsonProperty String roles) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User(){}

    public String getUserName() {return userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getRoles() {return roles;}
    public void setRoles(String roles) {this.roles = roles;}
}
