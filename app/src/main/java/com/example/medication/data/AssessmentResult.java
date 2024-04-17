package com.example.medication.data;

import java.util.Date;

public class AssessmentResult {
    private Integer id;

    private Date date;

    private Integer patient;

    private Integer score;

    private String bmiStatus;

    private String healthStatus;

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPatient() {
        return patient;
    }

    public Integer getScore() {
        return score;
    }

    public String getBmiStatus() {
        return bmiStatus;
    }

    public String getHealthStatus() {
        return healthStatus;
    }
}
