package com.example.medication.data;

import java.sql.Date;

public class PatientEntity {

    private Integer id;

    private String sId;

    private String fullName;

    private Date dateOfBirth;

    private String phoneNumber;

    private String avatar;
    private UserEntity userEntity;

    public PatientEntity() {
    }

    public PatientEntity(Integer id) {
        this.id = id;
    }

    public PatientEntity(Integer id, String sId, String fullName, Date dateOfBirth, String phoneNumber, String avatar, UserEntity userEntity) {
        this.id = id;
        this.sId = sId;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.userEntity = userEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
