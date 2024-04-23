package com.example.medication.quan.service;

import com.example.medication.quan.request.UserLoginRequest;
import com.example.medication.quan.respone.PatientRespone;
import com.example.medication.quan.respone.UserLoginRespone;
import com.example.medication.vinhquang.data.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
//    @POST("/register")
//    Call<String> registerUser(@Body UserEntity user);

    @POST("/login")
    Call<UserLoginRespone> postLoginForm(@Body UserLoginRequest userLoginRequest);


}
