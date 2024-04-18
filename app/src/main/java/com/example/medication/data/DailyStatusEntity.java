package com.example.medication.data;

import java.sql.Date;
import java.sql.Time;

public class DailyStatusEntity {

    private Integer id;

    private Date date;

    private Time time;

    private Integer status;

    private PatientEntity patientEntity;

    private PrescribedMedEntity prescribedMedEntity;

    public DailyStatusEntity() {
    }

    public DailyStatusEntity(Date date, Time time, Integer status, PatientEntity patientEntity, PrescribedMedEntity prescribedMedEntity) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.patientEntity = patientEntity;
        this.prescribedMedEntity = prescribedMedEntity;
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

    public PrescribedMedEntity getPrescribedMedEntity() {
        return prescribedMedEntity;
    }

    public void setPrescribedMedEntity(PrescribedMedEntity prescribedMedEntity) {
        this.prescribedMedEntity = prescribedMedEntity;
    }
}
