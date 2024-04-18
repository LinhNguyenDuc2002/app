package com.example.medication.data;

import java.sql.Date;

public class UserEntity {
    private Integer id;
   private String username;

    private String password;

    private String fullName;

    private Date dateOfBirth;

    private String phoneNumber;

    private String avatar;

    private Integer role;

    private NotiSettingEntity notiSettingEntity;
    public UserEntity() {
    }

    public UserEntity(Integer id) {
        this.id = id;
    }

    public UserEntity(Integer id, String username, String password, String fullName, Date dateOfBirth, String phoneNumber, String avatar, Integer role, NotiSettingEntity notiSettingEntity) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.role = role;
        this.notiSettingEntity = notiSettingEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public NotiSettingEntity getNotiSettingEntity() {
        return notiSettingEntity;
    }

    public void setNotiSettingEntity(NotiSettingEntity notiSettingEntity) {
        this.notiSettingEntity = notiSettingEntity;
    }
}
