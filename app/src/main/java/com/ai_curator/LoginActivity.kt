package com.ai_curator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ai_curator.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        val codeInput = binding.etCode
        val loginButton = binding.loginButton
    }
}