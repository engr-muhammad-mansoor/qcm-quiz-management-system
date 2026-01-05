package com.helloword.demo;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // Ajoutez d'autres propriétés au besoin

    // Constructeurs, getters et setters

    public Category() {
        // Constructeur par défaut nécessaire pour JPA
    }

    public Category(String name) {
        this.name = name;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Vous pouvez ajouter d'autres propriétés, getters et setters au besoin
}

