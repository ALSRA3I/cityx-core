package com.abdulrahman.cityxcore.model;

import org.springframework.data.annotation.Id;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Clearance clearance;

    // Constructors
    public User() {}

    public User(String username, String password, Role role, Clearance clearance) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.clearance = clearance;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Clearance getClearance() {
        return clearance;
    }

    public void setClearance(Clearance clearance) {
        this.clearance = clearance;
    }
}
