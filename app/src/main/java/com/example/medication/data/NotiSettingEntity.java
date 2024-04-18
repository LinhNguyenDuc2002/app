package com.example.medication.data;

import java.sql.Date;
import java.time.LocalTime;

public class NotiSettingEntity {

    private Integer id;

    private Date updatedAt;

    private Boolean status;

    private LocalTime preNoti;

    private Integer method;

    private UserEntity userEntity;
    public NotiSettingEntity(Integer id, Date updatedAt, Boolean status, LocalTime preNoti, Integer method, UserEntity userEntity) {
        this.id = id;
        this.updatedAt = updatedAt;
        this.status = status;
        this.preNoti = preNoti;
        this.method = method;
        this.userEntity = userEntity;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalTime getPreNoti() {
        return preNoti;
    }

    public void setPreNoti(LocalTime preNoti) {
        this.preNoti = preNoti;
    }

    public Integer getMethod() {
        return method;
    }

    public void setMethod(Integer method) {
        this.method = method;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
