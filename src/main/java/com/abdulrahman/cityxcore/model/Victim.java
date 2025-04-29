package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;


@Entity
@Table(name = "victims")
public class Victim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case aCase;

    public Victim() {}

    public Victim(String name, Case aCase) {
        this.name = name;
        this.aCase = aCase;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Case getACase() {
        return aCase;
    }

    public void setACase(Case aCase) {
        this.aCase = aCase;
    }
}
