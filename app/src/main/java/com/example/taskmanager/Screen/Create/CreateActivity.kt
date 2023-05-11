package com.example.taskmanager.Screen.Create

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ActivityCreateBinding

class CreateActivity : BaseActivity<ActivityCreateBinding, CreateViewModel>(ActivityCreateBinding::inflate, CreateViewModel::class.java) {

    override fun setupObservers() {
        viewModel.createLiveData.observe(this) {
            AlertDialog.Builder(this)
                .setTitle("Отлично")
                .setMessage("Ваша задача успешно создана")
                .setOnDismissListener { dialog ->
                    dialog.dismiss()

                    finish()
                }
                .setPositiveButton("OK") { dialog, id ->
                    dialog.dismiss()

                    finish()
                }.show()
        }
    }

    override fun setupView() {
        binding.create.setOnClickListener {
            viewModel.create(
                binding.title.text.toString(),
                binding.message.text.toString(),
                binding.login.text.toString(),
                binding.time.text.toString(),
            )
        }
    }
}