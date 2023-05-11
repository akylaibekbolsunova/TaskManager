package com.example.taskmanager.Views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.TaskItemBinding

class TaskListAdapter(var click: (String) -> Unit): RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    var tasks: ArrayList<Map<String, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), click)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    class TaskViewHolder(val binding: TaskItemBinding, var click: (String) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Map<String, String>) {
            binding.create.text = "Создатель: ${data["creater"]}"
            binding.title.text = "Заголовок: ${data["title"]}"
            binding.message.text = "Описание: ${data["message"]}"
            binding.login.text = "Назначено на: ${data["login"]}"
            binding.time.text = "Срок выполнения: ${data["time"]}"
            binding.status.text = "Статус задачи: ${data["status"]}"
            binding.createTime.text = "Дата создание задачи: ${data["time_create"]}"

            binding.contener.setOnClickListener {
                click(data["id"] as String)
            }
        }
    }
}
//Pair("creater", data["login"] ?: "not found"),
//Pair("title", title),
//Pair("message", message),
//Pair("login", login),
//Pair("time", time),
//Pair("status", "Не определен"),
//Pair("time_create", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())),
