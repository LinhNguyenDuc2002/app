package com.example.medication.service;

import com.example.medication.data.DTO.PrescriptionDto;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PrescriptionService {
    @GET("/prescription/{patientId}/allPrescription")
    public Call<List<PrescriptionDto>> getAllPrescriptions(@Path("patientId") Integer patientId);
}
