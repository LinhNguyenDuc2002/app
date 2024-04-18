package com.example.medication.data.DTO;

public class PrescribedMedDto {
    private Integer id;
    private String time;
    private String name;
    private Integer Quantity;
    private String unit;
    private String Note;
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
        return Quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getNote() {
        return Note;
    }

    public Integer getStatus() {
        return status;
    }
}
