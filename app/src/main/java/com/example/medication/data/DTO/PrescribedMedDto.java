package com.example.medication.data.DTO;

import java.sql.Time;

public class PrescribedMedDto {
    private Integer id;
    private Time time;
    private String name;
    private Integer Quantity;
    private String unit;
    private String Note;
    private Integer status;

    public PrescribedMedDto(Integer id,Time time,String name, Integer quantity, String unit, String note,Integer status) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.Quantity = quantity;
        this.unit = unit;
        this.Note = note;
        this.status = status;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public Integer getQuantity() {
        return Quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
