package com.example.medication.vinhquang.api;

import com.example.medication.data.DrinkingNotification;
import com.example.medication.vinhquang.data.AppointmentNotiResponse;
import com.example.medication.vinhquang.data.AppointmentRequest;
import com.example.medication.vinhquang.data.AppointmentResponse;
import com.example.medication.vinhquang.data.NotificationResponse;
import com.example.medication.vinhquang.data.SearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("notification/{id}/medicine")
    Call<DrinkingNotification> getDrinkingNoti(@Path("id") Integer id);

    @PUT("notification/{id}")
    Call<String> update(@Path("id") Integer id);
    @GET("/patient/{id}/search")
    Call<List<SearchResponse>> searchPatient(@Path("id") Integer id, @Query("name") String name);
    @GET("/doctor/search")
    Call<List<SearchResponse>> searchDoctor(@Query("name") String name);
    @POST("/appointment/set/{role}")
    Call<String> addApp(@Body AppointmentRequest appointmentRequest, @Path("role") Integer role);
    @PUT("/appointment/confirm/{notiId}")
    Call<String> confirm(@Path("notiId") Integer notiId, @Query("role") Integer role, @Query("status") Integer status);

    @GET("/appointment/all/{id}")
    Call<List<AppointmentResponse> >getAllApps(@Path("id") Integer id, @Query("role") Integer role);
    @GET("/appointment/{id}")
    Call<AppointmentResponse> getOneApp(@Path("id") Integer id);
}
