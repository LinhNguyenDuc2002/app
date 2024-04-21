package com.example.medication.data;

import java.util.List;

public class DetailStatistic {
    private Integer id;

    private String disease;

    private List<DailyResponse> dailyResponses;

    public Integer getId() {
        return id;
    }

    public String getDisease() {
        return disease;
    }

    public List<DailyResponse> getDailyResponses() {
        return dailyResponses;
    }
}
