package com.example.medication.vinhquang.data;

import java.util.Date;
import java.util.List;

public class DialogResponse {
    private Integer id;
    private Date updatedAt;
    private UserResponse patient;
    private UserResponse doctor;
    private List<MessageResponse> messageResponseList;

    public DialogResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public List<MessageResponse> getMessageResponseList() {
        return messageResponseList;
    }

    public void setMessageResponseList(List<MessageResponse> messageResponseList) {
        this.messageResponseList = messageResponseList;
    }
}
