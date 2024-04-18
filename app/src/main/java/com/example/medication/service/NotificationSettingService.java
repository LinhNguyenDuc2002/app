package com.example.medication.service;

import com.example.medication.data.NotificationSetting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificationSettingService {
    @GET("/setting/user/{id}")
    Call<NotificationSetting> getSettingStatus(@Path("id") Integer id);
}
