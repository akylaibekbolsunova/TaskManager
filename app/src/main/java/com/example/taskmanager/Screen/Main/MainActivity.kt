package com.example.taskmanager.Screen.Main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.R
import com.example.taskmanager.Screen.Create.CreateActivity
import com.example.taskmanager.Screen.Details.DetailsActivity
import com.example.taskmanager.Screen.Splash.SplashActivity
import com.example.taskmanager.Views.TaskListAdapter
import com.example.taskmanager.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(ActivityMainBinding::inflate, MainViewModel::class.java) {

    var adapter = TaskListAdapter { id ->
        var intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun setupObservers() {
        viewModel.userNameLiveData.observe(this) {
            binding.userFullName.text = it
        }

        viewModel.tasksNameLiveData.observe(this) {
            adapter.tasks = it

            adapter.notifyDataSetChanged()
        }
    }

    override fun setupView() {
        viewModel.getData()

        binding.createTask.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        binding.userFullName.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Внимание")
                .setMessage("Выйти из аккаунта?")
                .setPositiveButton("Да") { dialog, id ->
                    Firebase.auth.signOut()

                    finish()

                    startActivity(Intent(this, SplashActivity::class.java))

                    dialog.dismiss()
                }.setNegativeButton("Нет") { dialog, id ->
                    dialog.dismiss()
                }.show()

        }

        binding.tasksList.adapter = adapter
    }

    override fun onBackPressed() {

    }
}