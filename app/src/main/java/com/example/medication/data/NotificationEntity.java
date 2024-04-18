package com.example.medication.data;

import java.sql.Date;
import java.sql.Time;

public class NotificationEntity {

    private Integer id;

    private Integer type;

    private String title;

    private Date date;

    private Time time;

    private Integer status;

    private Integer role;

    private PatientEntity patientEntity;

    private UserEntity doctorEntity;

    private UserEntity receiverEntity;

    private AppointmentEntity appointmentEntity;

    private PrescribedMedEntity prescribedMedEntity;

    public NotificationEntity() {
    }

    public NotificationEntity(Integer id, Integer type, String title, Date date, Time time, Integer status, Integer role, PatientEntity patientEntity, UserEntity doctorEntity, UserEntity receiverEntity, AppointmentEntity appointmentEntity, PrescribedMedEntity prescribedMedEntity) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.date = date;
        this.time = time;
        this.status = status;
        this.role = role;
        this.patientEntity = patientEntity;
        this.doctorEntity = doctorEntity;
        this.receiverEntity = receiverEntity;
        this.appointmentEntity = appointmentEntity;
        this.prescribedMedEntity = prescribedMedEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public UserEntity getDoctorEntity() {
        return doctorEntity;
    }

    public void setDoctorEntity(UserEntity doctorEntity) {
        this.doctorEntity = doctorEntity;
    }

    public UserEntity getReceiverEntity() {
        return receiverEntity;
    }

    public void setReceiverEntity(UserEntity receiverEntity) {
        this.receiverEntity = receiverEntity;
    }

    public AppointmentEntity getAppointmentEntity() {
        return appointmentEntity;
    }

    public void setAppointmentEntity(AppointmentEntity appointmentEntity) {
        this.appointmentEntity = appointmentEntity;
    }

    public PrescribedMedEntity getPrescribedMedEntity() {
        return prescribedMedEntity;
    }

    public void setPrescribedMedEntity(PrescribedMedEntity prescribedMedEntity) {
        this.prescribedMedEntity = prescribedMedEntity;
    }
}
