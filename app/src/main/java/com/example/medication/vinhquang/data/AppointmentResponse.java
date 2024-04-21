package com.example.medication.vinhquang.data;

import java.util.Date;

public class AppointmentResponse {
    private Integer id;
    private Date dateTime;
    private String location;
    private Integer status;
    private UserResponse patient;
    private UserResponse doctor;

    public AppointmentResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserResponse getPatient() {
        return patient;
    }

    public void setPatient(UserResponse patient) {
        this.patient = patient;
    }

    public UserResponse getDoctor() {
        return doctor;
    }

    public void setDoctor(UserResponse doctor) {
        this.doctor = doctor;
    }
}
