package com.example.medication.data;

import java.util.Date;

public class Statistic {
    private Integer patientId;

    private Date startAt;

    private Date endAt;

    private Integer onTime;

    private Integer late;

    private Integer forget;

    public Integer getPatientId() {
        return patientId;
    }

    public Date getStartAt() {
        return startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public Integer getOnTime() {
        return onTime;
    }

    public Integer getLate() {
        return late;
    }

    public Integer getForget() {
        return forget;
    }
}
