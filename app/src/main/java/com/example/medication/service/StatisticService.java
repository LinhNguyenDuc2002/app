package com.example.medication.service;

import com.example.medication.data.DetailStatistic;
import com.example.medication.data.Statistic;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface StatisticService {
    @GET("/statistic/patient/{id}")
    Call<Statistic> getStatistic(@Path("id") Integer id, @QueryMap Map<String, String> parameters);

    @GET("/statistic/patient/{id}/detail")
    Call<List<DetailStatistic>> getDetailStatistic(@Path("id") Integer id, @QueryMap Map<String, String> parameters);
}
