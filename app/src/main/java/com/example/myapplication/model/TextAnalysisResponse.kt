package com.example.myapplication.model

// Модель данных для ответа сервера при анализе текста
data class TextAnalysisResponse(
    val isGenerated: Boolean, // Флаг, указывающий, сгенерирован ли текст
    val confidence: Double,   // Уровень уверенности в результате (от 0 до 1)
    val result: String       // Результат анализа
) 