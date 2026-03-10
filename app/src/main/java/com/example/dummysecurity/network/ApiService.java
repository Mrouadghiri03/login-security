package com.example.dummysecurity.network;

import com.example.dummysecurity.model.LoginRequest;
import com.example.dummysecurity.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}