package com.example.medication.service;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {
    public final static String API_BASE_URL = "http://10.0.2.2:8082";

    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(new OkHttpClient.Builder().build())
            .addConverterFactory(
                    GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()
                    )
            )
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
