package com.example.medication.data;

import java.sql.Date;
import java.sql.Time;

public class AppointmentEntity {

    private Integer id;

    private Date date;

    private Time time;

    private String location;

    private Integer status;

    private PatientEntity patientEntity;

    private UserEntity userEntity;

    public AppointmentEntity() {
    }

    public AppointmentEntity(Integer id, Date date, Time time, String location, Integer status, PatientEntity patientEntity, UserEntity userEntity) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
        this.patientEntity = patientEntity;
        this.userEntity = userEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
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

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
