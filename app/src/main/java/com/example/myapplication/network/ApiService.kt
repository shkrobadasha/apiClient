package com.example.myapplication.network

import com.example.myapplication.model.LoginRequest
import com.example.myapplication.model.TextAnalysisResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// Интерфейс для работы с API сервера
interface ApiService {
    // Метод для авторизации пользователя
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<Unit>

    // Метод для анализа текста
    @POST("analyze")
    suspend fun analyzeText(@Body text: String): Response<TextAnalysisResponse>
} 