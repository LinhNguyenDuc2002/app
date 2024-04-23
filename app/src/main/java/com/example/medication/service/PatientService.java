package com.example.medication.service;


import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.DTO.SearchResponse;
import com.example.medication.quan.respone.PatientRespone;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PatientService {
    @GET("/patientdto/{patientId}")
    public Call<PatientDto> findById(@Path("patientId") Integer patientId);

    @GET("/patientdto/{name}")
    Call<List<PatientDto>> searchByName(@Path("name") String name);

    @GET("/{id}/patient")
    Call<List<PatientRespone>> getListPatients(@Path("id") Integer Id);
}
