package com.example.medication.service;


import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.PatientEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientService {
    @GET("/patientdto/{patientId}")
    public Call<PatientDto> findById(@Path("patientId") Integer patientId);
}
