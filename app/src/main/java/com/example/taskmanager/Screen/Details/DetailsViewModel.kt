package com.example.taskmanager.Screen.Details

import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.Base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DetailsViewModel: BaseViewModel() {

    var infoLiveData = MutableLiveData<Map<String, String>>()

    fun getDetails(id: String) {
        showProgress(true)

        Firebase.database.getReference("TASK").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val array = snapshot.value as ArrayList<Map<String, String>>

                    array.forEach {
                        if (id == it["id"]) {
                            infoLiveData.value = it
                        }
                    }
                }

                showProgress(false)
            }

            override fun onCancelled(error: DatabaseError) {
                showProgress(false)
            }
        })
    }

    fun saveChange(id: String, status: String, login: String, time: String) {
        if (status.isEmpty()) {
            showAlert("Статус пустой")

            return
        }

        if (login.isEmpty()) {
            showAlert("Логин пустой")

            return
        }

        if (time.isEmpty()) {
            showAlert("Время исполнение пустое")

            return
        }

        showProgress(true)

        Firebase.database.getReference("ALL_USERS").child("login-${login}").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    Firebase.database.getReference("TASK").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.value != null) {
                                var array = snapshot.value as ArrayList<Map<String, String>>

                                array.forEachIndexed { index, map ->
                                    if (id == map["id"]) {

                                        array[index] = mapOf(
                                            Pair("creater", map["creater"] ?: ""),
                                            Pair("title", map["title"] ?: ""),
                                            Pair("message", map["message"] ?: ""),
                                            Pair("login", login),
                                            Pair("time", time),
                                            Pair("status", status),
                                            Pair("time_create", map["time_create"] ?: ""),
                                            Pair("id", map["id"] ?: ""),
                                        )
                                    }
                                }

                                Firebase.database.getReference("TASK").setValue(array)

                                showAlert("Задача успошно изменена")
                            }

                            showProgress(false)
                        }

                        override fun onCancelled(error: DatabaseError) {
                            showProgress(false)
                        }
                    })
                } else {
                    showAlert("Login пользователя не найден")

                    showProgress(false)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showProgress(false)
            }
        })
    }
}