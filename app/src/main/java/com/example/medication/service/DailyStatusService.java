package com.example.medication.service;

import com.example.medication.data.DTO.PrescribedMedDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DailyStatusService {
    @Headers({"Content-Type: application/json"})
    @POST("/dailyStatus/updateDailyStatus/{patientId}/{status}")
    Call<Void> updateDailyStatus(
            @Body PrescribedMedDto prescribedMedDto,
            @Path("patientId") Integer patientId,
            @Path("status") Integer status
    );
}
