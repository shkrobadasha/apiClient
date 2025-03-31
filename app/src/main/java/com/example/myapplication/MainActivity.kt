package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.AnalyzeClient
import com.example.myapplication.api.AnalyzeRequest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // Объявление UI элементов
    private lateinit var textInput: EditText
    private lateinit var sendButton: Button
    private lateinit var resultText: TextView
    private lateinit var confidenceText: TextView
    private val apiClient = ApiClient() // Используем наш ApiClient
    private val analyzeClient = AnalyzeClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация UI элементов
        textInput = findViewById(R.id.textInput)
        sendButton = findViewById(R.id.sendButton)
        resultText = findViewById(R.id.resultText)
        confidenceText = findViewById(R.id.confidenceText)

        // Обработчик нажатия кнопки отправки
        sendButton.setOnClickListener {
            val text = textInput.text.toString()
            if (text.isBlank()) {
                Toast.makeText(this, "Пожалуйста, введите текст", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Асинхронный запрос на анализ текста
            analyzeText(text)
        }
    }

    private fun analyzeText(text: String) {
        lifecycleScope.launch {
            try {
                val request = AnalyzeRequest(text)
                val response = apiClient.analysisApi.analyzeText(request)
                
                if (response.isSuccessful) {
                    val result = response.body()!!
                    // Отображение результата анализа
                    resultText.text = if (result.isGenerated) {
                        "Результат: Текст сгенерирован ИИ"
                    } else {
                        "Результат: Текст написан человеком"
                    }
                    confidenceText.text = "Уверенность: ${(result.confidence * 100).toInt()}%"
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка анализа (${response.code()}): $errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "Ошибка подключения: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}