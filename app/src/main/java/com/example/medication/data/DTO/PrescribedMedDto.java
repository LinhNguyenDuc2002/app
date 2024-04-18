package com.example.medication.data.DTO;

public class PrescribedMedDto {
    private Integer id;
    private String time;
    private String name;
    private Integer quantity;
    private String unit;
    private String note;
    private Integer status;

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
