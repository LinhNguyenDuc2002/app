package com.example.medication.service;

import com.example.medication.data.NotificationSetting;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface NotificationSettingService {
    @GET("/setting/user/{id}")
    Call<NotificationSetting> getSettingStatus(@Path("id") Integer id);

    @PUT("/setting/user/{id}")
    Call<NotificationSetting> updateSetting(@Path("id") Integer id, @QueryMap Map<String, Object> parameters);
}
