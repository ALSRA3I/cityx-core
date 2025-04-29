package com.abdulrahman.cityxcore.dto;

import com.abdulrahman.cityxcore.model.Clearance;
import com.abdulrahman.cityxcore.model.Role;


public class UserDTO {
    private Long id;
    private String username;
    private String password; // In production, consider omitting this from responses
    private Role role;
    private Clearance clearance;

    public UserDTO() {}

    public UserDTO(Long id, String username, String password, Role role, Clearance clearance) {
        this.id = id;
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
