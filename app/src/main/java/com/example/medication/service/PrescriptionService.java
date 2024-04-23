package com.example.medication.service;

import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.data.DTO.PrescriptionDto;
import com.example.medication.data.PrescriptionEntity;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface PrescriptionService {
    @GET("/prescription/{patientId}/allPrescription")
    public Call<List<PrescriptionDto>> getAllPrescriptions(@Path("patientId") Integer patientId);

    @GET("/prescription/patient/{id}")
    Call<List<PrescriptionDto>> getPrescriptionByDate(@Path("id") Integer id, @QueryMap Map<String, String> map);

    @POST("/prescription/{doctorId}/addNewPrescription/{patientId}/{disease}")
    Call<Void> addNewPrescription(@Path("doctorId") Integer doctorId,
                                  @Path("patientId") Integer patientId,
                                  @Path("disease") String disease,
                                  @Body List<PrescribedMedDto> prescribedMedDtoList);

}
