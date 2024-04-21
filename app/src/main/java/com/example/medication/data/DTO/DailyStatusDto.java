package com.example.medication.data.DTO;


import java.sql.Time;

public class DailyStatusDto {
    private Integer id;
    private String date;
    private String time;
    private Integer status;
    private Integer patientId;
    private Integer prescribedMedId;

    public DailyStatusDto(Integer id, String date, String time, Integer status, Integer patientId, Integer prescribedMedId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.patientId = patientId;
        this.prescribedMedId = prescribedMedId;
    }

    public DailyStatusDto() {
    }

    public DailyStatusDto(String date, Time time, Integer status, Integer prescribedMedId, Integer patientId) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getPrescribedMedId() {
        return prescribedMedId;
    }

    public void setPrescribedMedId(Integer prescribedMedId) {
        this.prescribedMedId = prescribedMedId;
    }
}

