package com.example.medication.service;

import com.example.medication.data.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AssessmentService {
    @GET("/assessment/questions")
    public Call<List<Question>> getListQuestions();
}
