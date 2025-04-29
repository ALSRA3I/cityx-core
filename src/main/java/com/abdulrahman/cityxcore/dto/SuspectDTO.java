package com.abdulrahman.cityxcore.dto;

public class SuspectDTO {
    private Long id;
    private String name;

    public SuspectDTO() {}

    public SuspectDTO(Long id, String name) {
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
