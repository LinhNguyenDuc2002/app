package com.example.medication.data;

import java.sql.Date;
import java.time.LocalTime;

public class NotificationSetting {
    private Integer id;

    private Date updatedAt;

    private Boolean status;

    private LocalTime preNoti;

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

    public LocalTime getPreNoti() {
        return preNoti;
    }

    public Integer getMethod() {
        return method;
    }
}
