package com.apishoppage.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @JsonProperty
    private UUID id;


    @Column(nullable = false, unique = false)
    @NotBlank
    @NotEmpty
    @JsonProperty
    private String name;

    @Column(nullable = false, unique = false)
    @Min(18)
    @Max(150)
    @Positive
    @JsonProperty
    private int age;

    @Column(nullable = false)
    @JsonProperty
    private String city;

    @Column(nullable = false)
    @Email
    @JsonProperty
    private String email;

    @Column(nullable = false)
    @JsonProperty
    private String phone;

}
