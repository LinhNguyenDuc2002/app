package com.example.medication.data;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PrescribedMedEntity {

    private Integer id;

    private Time time;

    private Integer quantity;

    private String unit;

    private String note;


    private Integer status;

    private PrescriptionEntity prescriptionEntity;

    private MedicationEntity medicationEntity;

    private List<DailyStatusEntity> dailyStatusEntityList = new ArrayList<>();

    private List<NotificationEntity> notificationEntityList = new ArrayList<>();

    public PrescribedMedEntity() {
    }
    public PrescribedMedEntity(Integer id) {
        this.id = id;
    }

    public PrescribedMedEntity(Integer id, Time time, Integer quantity, String unit, String note, Integer status,
                               PrescriptionEntity prescriptionEntity, MedicationEntity medicationEntity,
                               List<DailyStatusEntity> dailyStatusEntityList, List<NotificationEntity> notificationEntityList) {
        this.id = id;
        this.time = time;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
        this.status = status;
        this.prescriptionEntity = prescriptionEntity;
        this.medicationEntity = medicationEntity;
        this.dailyStatusEntityList = dailyStatusEntityList;
        this.notificationEntityList = notificationEntityList;
    }

    public PrescribedMedEntity(Time time, Integer quantity, String unit, String note, Integer status) {
        this.time = time;
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public PrescriptionEntity getPrescriptionEntity() {
        return prescriptionEntity;
    }

    public void setPrescriptionEntity(PrescriptionEntity prescriptionEntity) {
        this.prescriptionEntity = prescriptionEntity;
    }

    public MedicationEntity getMedicationEntity() {
        return medicationEntity;
    }

    public void setMedicationEntity(MedicationEntity medicationEntity) {
        this.medicationEntity = medicationEntity;
    }

    public List<DailyStatusEntity> getDailyStatusEntityList() {
        return dailyStatusEntityList;
    }

    public void setDailyStatusEntityList(List<DailyStatusEntity> dailyStatusEntityList) {
        this.dailyStatusEntityList = dailyStatusEntityList;
    }

    public List<NotificationEntity> getNotificationEntityList() {
        return notificationEntityList;
    }

    public void setNotificationEntityList(List<NotificationEntity> notificationEntityList) {
        this.notificationEntityList = notificationEntityList;
    }
}
