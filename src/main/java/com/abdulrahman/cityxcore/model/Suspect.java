package com.abdulrahman.cityxcore.model;

import jakarta.persistence.*;


@Entity
@Table(name = "suspects")
public class Suspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Minimal field for suspect name or details.
    private String name;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case aCase;

    public Suspect() {}

    public Suspect(String name, Case aCase) {
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
