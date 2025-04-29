package com.abdulrahman.cityxcore.dto;

public class WitnessDTO {
    private Long id;
    private String name;

    public WitnessDTO() {}

    public WitnessDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
