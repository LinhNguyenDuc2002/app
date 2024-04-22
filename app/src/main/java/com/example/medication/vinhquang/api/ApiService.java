package com.example.medication.vinhquang.api;

import com.example.medication.data.DrinkingNotification;
import com.example.medication.vinhquang.data.AppointmentNotiResponse;
import com.example.medication.vinhquang.data.AppointmentRequest;
import com.example.medication.vinhquang.data.AppointmentResponse;
import com.example.medication.vinhquang.data.DialogResponse;
import com.example.medication.vinhquang.data.MedicationResponse;
import com.example.medication.vinhquang.data.MessageRequest;
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

    @GET("/medication/search")
    Call<List<SearchResponse>> searchMed(@Query("name") String name);

    @GET("/medication/{id}")
    Call<MedicationResponse> getOneMed(@Path("id") Integer id);

    @GET("/medication/interactions/{id}")
    Call<List<SearchResponse>> getAllInteraction(@Path("id") Integer id);

    @GET("/chat/dialog/all/{id}")
    Call<List<DialogResponse>> getAllDialog(@Path("id") Integer id, @Query("role") Integer role) ;

    @GET("/chat/dialog/{id}")
    Call<DialogResponse> getOneDialog(@Path("id") Integer id);

    @POST("/chat/send")
    Call<String> sendMessage(@Body MessageRequest messageRequest);
    @GET("/chat/dialog/{fId}/{sId}")
    Call<Integer> createOrGetDialog(@Path("fId") Integer fId, @Path("sId") Integer sId, @Query("role") Integer role);
}
