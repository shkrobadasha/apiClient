package com.example.myapplication.model

// Модель данных для запроса авторизации
data class LoginRequest(
    val username: String, // Имя пользователя
    val password: String  // Пароль
) 