package com.example.taskmanager.Screen.Details

import com.example.taskmanager.Base.BaseActivity
import com.example.taskmanager.databinding.ActivityDetailsBinding

class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>(ActivityDetailsBinding::inflate, DetailsViewModel::class.java) {

    override fun setupObservers() {
        viewModel.infoLiveData.observe(this) {
            binding.title.text = it["title"]
            binding.message.text = it["message"]
            binding.loginEdit.setText(it["login"])
            binding.create.text = "Задачу создал ${it["creater"]}"
            binding.timeCreate.text = "время создание задачи ${it["time_create"]}"
            binding.statusEdit.setText(it["status"])
            binding.timeEdit.setText(it["time"])
        }
    }

//    Pair("creater", data["login"] ?: "not found"),
//    Pair("title", title),
//    Pair("message", message),
//    Pair("login", login),
//    Pair("time", time),
//    Pair("status", "Не определен"),
//    Pair("time_create", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())),
//    Pair("id", Random().nextInt().toString()),

    override fun setupView() {
        viewModel.getDetails(intent.getStringExtra("id").toString())

        binding.change.setOnClickListener {
            viewModel.saveChange(
                intent.getStringExtra("id").toString(),
                binding.statusEdit.text.toString(),
                binding.loginEdit.text.toString(),
                binding.timeEdit.text.toString()
            )
        }
    }
}