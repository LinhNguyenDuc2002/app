package com.example.medication.data;

import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.DTO.PrescribedMedDto;

import java.util.Date;

public class DrinkingNotification {
    private Integer id;
    private Integer type;
    private String title;
    private String date;
    private String time;
    private Integer role;
    private PatientDto patient;
    private PrescribedMedDto prescribedMed;

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getRole() {
        return role;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public PrescribedMedDto getPrescribedMed() {
        return prescribedMed;
    }
}
