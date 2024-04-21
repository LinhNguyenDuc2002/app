package com.example.medication.data;

import java.util.Date;

public class DailyResponse {
    private Integer id;

    private String name;

    private Integer quantity;

    private String unit;

    private Date date;

    private String time;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getTime() {
        return time;
    }

    public Integer getStatus() {
        return status;
    }
}
