package com.example.myapplication.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// Общий URL для всех запросов
private const val AUTH_URL = "http://172.20.10.2:8081/"  // текущий IP в мобильной сети
private const val NEURAL_URL = "https://7d98-34-106-57-61.ngrok-free.app/"  // новый URL для нейронки
private const val BASE_URL = "http://172.20.10.2:8081/"  //мой компьютер

// Запросы/ответы для авторизации
/*data class LoginRequest(
    val username: String,
    val password: String
)*/

data class LoginResponse(
    val token: String
)

// Запросы/ответы для анализа текста
data class AnalyzeRequest(
    val text: String
)

// Разделим интерфейсы для разных серверов
interface AuthService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body request: LoginRequest): Response<Unit>
}

interface TextAnalysisService {
    @POST("analyze")
    suspend fun analyzeText(@Body request: AnalyzeRequest): Response<AnalyzeResponse>
}

// Класс для ответа от сервера анализа текста
data class AnalyzeResponse(
    val isGenerated: Boolean,
    val confidence: Float
)

// Клиенты для разных серверов
class AuthClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(AUTH_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AuthService = retrofit.create(AuthService::class.java)
}

class AnalyzeClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl(NEURAL_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: TextAnalysisService = retrofit.create(TextAnalysisService::class.java)
}

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<Unit>

    @POST("register")
    suspend fun register(@Body request: LoginRequest): Response<Unit>
}