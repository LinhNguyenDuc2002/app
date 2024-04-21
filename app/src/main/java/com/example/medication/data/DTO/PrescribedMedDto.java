package com.example.medication.data.DTO;

public class PrescribedMedDto {
    private Integer id;
    private String time;
    private String name;
    private Integer quantity;
    private String unit;
    private String note;
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getNote() {
        return note;
    }

    public Integer getStatus() {
        return status;
    }
}
