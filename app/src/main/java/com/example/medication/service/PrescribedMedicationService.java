package com.example.medication.service;

import com.example.medication.data.DTO.PrescribedMedDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PrescribedMedicationService {
    @GET("/prescriptionMed/findDaylyMedication/{id}")
    Call<List<PrescribedMedDto>> getDailyMedication(@Path("id") Integer id);

    @GET("/prescriptionMed/findAll/{prescriptionId}")
    Call<List<PrescribedMedDto>> findAllPrescribedMedByPrecriptionId(@Path("prescriptionId") Integer prescriptionId);

    @PUT("prescriptionMed/sync/{prescriptionId}")
    Call<Void> SyncMedicationToDailyByPrescription(@Path("prescriptionId") Integer prescriptionId, @Body List<PrescribedMedDto> data);

    @PUT("prescriptionMed/deletePrescribedMedication/{precribedMedId}")
    Call<Void> updatePrescribedMedEndById(@Path("precribedMedId") Integer precribedMedId);
}
