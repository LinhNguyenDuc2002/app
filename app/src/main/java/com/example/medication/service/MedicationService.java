package com.example.medication.service;

import com.example.medication.data.DTO.SearchResponse;
import com.example.medication.data.MedicationEntity;

import org.checkerframework.checker.units.qual.C;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicationService {

    @PUT("/prescriptionMed/updateTime/{prescribedMedId}/{time}")
    Call<Void> updateTime(@Path("prescribedMedId") Integer prescribedMedId,@Path("time") String time);
}
