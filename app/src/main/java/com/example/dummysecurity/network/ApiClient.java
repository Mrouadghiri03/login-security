package com.example.dummysecurity.network;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://dummyjson.com/";
    private static ApiService apiService;
/*
    public static ApiService getApiService() {
        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

 */
    private static Retrofit retrofit = null;

    public static ApiService getApiService(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    // On récupère le token stocké dans les SharedPreferences
                    SharedPreferences pref = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    String token = pref.getString("token", "");

                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + token)
                            .build();
                    return chain.proceed(newRequest);
                }).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://dummyjson.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}
