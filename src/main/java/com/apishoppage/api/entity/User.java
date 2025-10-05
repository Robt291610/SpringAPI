package com.apishoppage.api.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;



@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "User_Name")
    @NonNull
    @NotBlank
    @JsonProperty
    private String userName;

    @Column(name = "Password")
    @NonNull
    @NotBlank
    @JsonProperty
    private String password;

    @Column(unique = true, nullable = false, name = "Email")
    @Email
    @JsonProperty
    private String email;

    @Column(nullable = false, name = "Roles")
    @JsonProperty
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens;

}
