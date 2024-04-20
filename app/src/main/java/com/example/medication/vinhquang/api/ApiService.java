package com.example.medication.vinhquang.api;

import com.example.medication.vinhquang.data.AppointmentNotiResponse;
import com.example.medication.vinhquang.data.NotificationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @PUT("/user/token/{id}")
    Call<String> setToken(@Path("id") Integer id, @Query("token") String token);

    @GET("notification/{id}/old")
    Call<List<NotificationResponse>> getListOldNoti(@Path("id") Integer id);
    @GET("notification/{id}")
    Call<AppointmentNotiResponse> getAppointmentNoti(@Path("id") Integer id);

    @PUT("notification/{id}")
    Call<String> update(@Path("id") Integer id);
}
