package com.example.myapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val authRetrofit = Retrofit.Builder()
        .baseUrl("http://192.168.3.11:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val analysisRetrofit = Retrofit.Builder()
        .baseUrl("https://d14d-35-202-63-37.ngrok-free.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authApi: AuthService = authRetrofit.create(AuthService::class.java)
    val analysisApi: TextAnalysisService = analysisRetrofit.create(TextAnalysisService::class.java)
} 