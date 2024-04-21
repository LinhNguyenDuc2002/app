package com.example.medication.vinhquang.util;

import com.example.medication.vinhquang.data.NotificationFirebase;

import java.util.ArrayList;
import java.util.List;

public class GlobalValues {
    private static GlobalValues instance;
    private String token;
    private Integer userId = 1;
    private List<NotificationFirebase> notificationList = new ArrayList<>();

    private GlobalValues() {
        // Khởi tạo các giá trị mặc định
    }

    public static GlobalValues getInstance() {
        if (instance == null) {
            instance = new GlobalValues();
        }
        return instance;
    }

    public static void setInstance(GlobalValues instance) {
        GlobalValues.instance = instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<NotificationFirebase> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationFirebase> notificationList) {
        this.notificationList = notificationList;
    }
}
