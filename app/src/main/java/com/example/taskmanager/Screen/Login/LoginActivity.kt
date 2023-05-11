package com.example.taskmanager.Screen.Login

import android.content.Intent
import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.Screen.Main.MainActivity
import com.example.taskmanager.Screen.Register.RegisterActivity
import com.example.taskmanager.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(ActivityLoginBinding::inflate, LoginViewModel::class.java) {

    override fun setupObservers() {
        viewModel.loginLiveData.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun setupView() {
        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.login.setOnClickListener {
            viewModel.login(
                binding.editLogin.text.toString(),
                binding.editPassword.text.toString()
            )
        }
    }
}