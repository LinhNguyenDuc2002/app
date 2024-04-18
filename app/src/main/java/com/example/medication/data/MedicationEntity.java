package com.example.medication.data;

public class MedicationEntity {

    private Integer id;

    private String name;

    private String sideEffect;

    private String effect;

    private String description;

    private String link;

    public MedicationEntity() {
    }

    public MedicationEntity(Integer id, String name, String sideEffect, String effect, String description, String link) {
        this.id = id;
        this.name = name;
        this.sideEffect = sideEffect;
        this.effect = effect;
        this.description = description;
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSideEffect() {
        return sideEffect;
    }

    public void setSideEffect(String sideEffect) {
        this.sideEffect = sideEffect;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
