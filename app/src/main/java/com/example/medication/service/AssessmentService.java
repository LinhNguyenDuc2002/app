package com.example.medication.service;

import com.example.medication.data.AssessmentResult;
import com.example.medication.data.Question;
import com.example.medication.data.request.AnswerForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AssessmentService {
    @GET("/assessment/questions")
    Call<List<Question>> getListQuestions();

    @POST("/assessment/{id}")
    Call<AssessmentResult> postAnswer(@Path("id") Integer id, @Body AnswerForm answerForm);
}
