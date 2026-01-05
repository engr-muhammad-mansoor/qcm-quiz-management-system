package com.helloword.demo;

import jakarta.persistence.*;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cha;
    private String chab;

    private String chac;

    private String chad;


    @ManyToOne
    @JoinColumn(name="category _id")

    private Category category;



    public Question() {

    }



    public Question(String name, String cha, String chab, String chac, String chad,Category category) {
        this.name = name;
        this.cha = cha;
        this.chab = chab;
        this.chac = chac;
        this.chad = chad;

        this.category = category;
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

    public String getChab() {
        return chab;
    }

    public void setChab(String chab) {
        this.chab = chab;
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChac() {
        return chac;
    }

    public void setChac(String chac) {
        this.chac = chac;
    }

    public String getChad() {
        return chad;
    }

    public void setChad(String chad) {
        this.chad = chad;
    }





    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

