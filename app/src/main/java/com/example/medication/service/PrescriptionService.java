package com.example.medication.service;

import com.example.medication.data.DTO.PrescriptionDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface PrescriptionService {
    @GET("/{patientId}/allPrescription")
    public Call<List<PrescriptionDto>> getAllPrescriptions(@Path("patientId") Integer patientId);

    @GET("/prescription/patient/{id}")
    Call<List<PrescriptionDto>> getPrescriptionByDate(@Path("id") Integer id, @QueryMap Map<String, String> map);
}
