package com.example.medication.data.DTO;

import java.util.Date;

public class PrescriptionDto {
    private Integer id;
    private String disease;
    private Date createdAt;
    private String doctorName;
    private String patientName;
    private Integer status;

    public PrescriptionDto(Integer id, String disease, Date createdAt, String doctorName, String patientName, Integer status) {
        this.id = id;
        this.disease = disease;
        this.createdAt = createdAt;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.status = status;
    }

    public PrescriptionDto() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
