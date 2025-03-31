package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.api.ApiClient
import com.example.myapplication.api.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    // Объявление UI элементов
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private val apiClient = ApiClient()  // Используем существующий ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Инициализация UI элементов
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)

        // Обработчик нажатия кнопки входа
        loginButton.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val username = usernameInput.text.toString()
        val password = passwordInput.text.toString()

        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            return
        }

        loginButton.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = apiClient.authApi.login(LoginRequest(username, password))
                if (response.isSuccessful && response.body() != null) {
                    // Можно сохранить токен если нужно
                    // val token = response.body()!!.token
                    
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                } else {
                    showError("Ошибка входа: ${response.code()}")
                }
            } catch (e: Exception) {
                showError("Ошибка: ${e.message}")
                e.printStackTrace()
            } finally {
                loginButton.isEnabled = true
            }
        }
    }

    private fun showError(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}