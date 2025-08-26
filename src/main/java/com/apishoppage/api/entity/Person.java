package com.apishoppage.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    @Column(nullable = false, unique = false)
    @NotBlank
    @NotEmpty
    private String name;

    @Column(nullable = false, unique = false)
    @Min(18)
    @Max(150)
    @Positive
    private int age;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String phone;

    public Person(
                  @JsonProperty("name") String name,
                  @JsonProperty("age")  int age,
                  @JsonProperty("city") String city,
                  @JsonProperty("email") String email,
                  @JsonProperty("phone") String phone) {
        this.name = name;
        this.age = age;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public Person() {}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}


}
