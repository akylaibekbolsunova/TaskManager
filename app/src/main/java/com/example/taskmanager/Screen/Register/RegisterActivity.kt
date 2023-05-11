package com.example.taskmanager.Screen.Register

import android.content.Intent
import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.Screen.Main.MainActivity
import com.example.taskmanager.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>(ActivityRegisterBinding::inflate, RegisterViewModel::class.java) {

    override fun setupObservers() {
        viewModel.registerLiveData.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun setupView() {
        binding.register.setOnClickListener {
            viewModel.register(
                binding.email.text.toString(),
                binding.userFullName.text.toString(),
                binding.login.text.toString(),
                binding.password.text.toString(),
                binding.repitPassword.text.toString(),
                binding.job.text.toString(),
            )
        }
    }
}