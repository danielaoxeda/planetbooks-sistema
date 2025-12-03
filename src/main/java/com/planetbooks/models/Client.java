package com.planetbooks.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name is required")
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = "The father's surname is mandatory")
    @Size(min = 2, max = 50)
    private String last_name_father;

    @NotBlank(message = "The mother's surname is mandatory")
    @Size(min = 2, max = 50)
    private String last_name_mother;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "User is required")
    @Size(min = 5, max = 50)
    private String user;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be at least 6 characters")
    private String password;

    @Min(value = 18, message = "Must be over 18 years old")
    private int age;

    @NotBlank(message = "Country is required")
    private String country;

    @Column(name = "registration_date")
    private LocalDate registration_date;

    private int purchases;
    private int sessions;
    private String role;
    private boolean active = true;

    // ===== Getters y Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLast_name_father() { return last_name_father; }
    public void setLast_name_father(String last_name_father) { this.last_name_father = last_name_father; }

    public String getLast_name_mother() { return last_name_mother; }
    public void setLast_name_mother(String last_name_mother) { this.last_name_mother = last_name_mother; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDate getRegistration_date() { return registration_date; }
    public void setRegistration_date(LocalDate registration_date) { this.registration_date = registration_date; }

    public int getPurchases() { return purchases; }
    public void setPurchases(int purchases) { this.purchases = purchases; }

    public int getSessions() { return sessions; }
    public void setSessions(int sessions) { this.sessions = sessions; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
