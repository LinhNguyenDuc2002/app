package com.example.medication.data;

import java.util.Date;
import java.time.LocalTime;

public class NotificationSetting {
    private Integer id;

    private Date updatedAt;

    private Boolean status;

    private String preNoti;

    private Integer method;

    public Integer getId() {
        return id;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getPreNoti() {
        return preNoti;
    }

    public Integer getMethod() {
        return method;
    }
}
