package com.example.medication.vinhquang.util;

import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.data.SearchResponse;

import java.util.ArrayList;
import java.util.List;

public class GlobalValues {
    private static GlobalValues instance;
    private String token;
    private Integer userId;
    private Integer role;
    private List<NotificationFirebase> notificationList = new ArrayList<>();
    private Integer searchType;
    private SearchResponse docterSearch;
    private SearchResponse patientSearch;

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

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public SearchResponse getDocterSearch() {
        return docterSearch;
    }

    public void setDocterSearch(SearchResponse docterSearch) {
        this.docterSearch = docterSearch;
    }

    public SearchResponse getPatientSearch() {
        return patientSearch;
    }

    public void setPatientSearch(SearchResponse patientSearch) {
        this.patientSearch = patientSearch;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
