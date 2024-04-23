package com.example.medication.data;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionEntity {

    private Integer id;

    private String disease;

    private Date createdAt;


    private Date updatedAt;


    private Date expiredAt;

    private Integer status;


    private UserEntity userEntity;

    private PatientEntity patientEntity;

    private List<PrescribedMedEntity> prescribedMedEntityList = new ArrayList<>();
    public PrescriptionEntity(String disease, UserEntity userEntity
            , PatientEntity patientEntity, List<PrescribedMedEntity> prescribedMedEntityList) {
        this.disease = disease;
        this.createdAt = new Date(System.currentTimeMillis());
        this.userEntity = userEntity;
        this.patientEntity = patientEntity;
        this.prescribedMedEntityList = prescribedMedEntityList;
    }

    public PrescriptionEntity(Integer id, String disease, Date createdAt, Date updatedAt, Date expiredAt, Integer status, UserEntity userEntity, PatientEntity patientEntity, List<PrescribedMedEntity> prescribedMedEntityList) {
        this.id = id;
        this.disease = disease;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.expiredAt = expiredAt;
        this.status = status;
        this.userEntity = userEntity;
        this.patientEntity = patientEntity;
        this.prescribedMedEntityList = prescribedMedEntityList;
    }

    public PrescriptionEntity(String disease) {
        this.createdAt = new Date(System.currentTimeMillis());
        this.updatedAt = new Date(System.currentTimeMillis());
        this.expiredAt = new Date(System.currentTimeMillis());
        this.status = 0;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public List<PrescribedMedEntity> getPrescribedMedEntityList() {
        return prescribedMedEntityList;
    }

    public void setPrescribedMedEntityList(List<PrescribedMedEntity> prescribedMedEntityList) {
        this.prescribedMedEntityList = prescribedMedEntityList;
    }
}
