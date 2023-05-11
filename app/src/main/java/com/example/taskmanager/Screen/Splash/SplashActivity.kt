package com.example.taskmanager.Screen.Splash

import android.content.Intent
import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.Screen.Login.LoginActivity
import com.example.taskmanager.Screen.Main.MainActivity
import com.example.taskmanager.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(ActivitySplashBinding::inflate, SplashViewModel::class.java) {

    override fun setupObservers() {
        viewModel.userStatusObserver.observe(this) { isLogin ->
            if (isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }

            finish()
        }
    }

    override fun setupView() {
        viewModel.checkUserStatus()
    }
}