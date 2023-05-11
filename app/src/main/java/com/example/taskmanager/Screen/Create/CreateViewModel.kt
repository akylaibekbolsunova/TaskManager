package com.example.taskmanager.Screen.Create

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

class CreateViewModel: BaseViewModel() {

    var createLiveData = MutableLiveData<Boolean>()

    fun create(title: String, message: String, login: String, time: String) {
        if (title.isEmpty()) {
            showAlert("Заголовок не заполнен")

            return
        }

        if (message.isEmpty()) {
            showAlert("Описание не заполнено")

            return
        }

        if (login.isEmpty()) {
            showAlert("Логин не заполнен")

            return
        }

        if (time.isEmpty()) {
            showAlert("Время исполнение не заполненно")

            return
        }

        showProgress(true)

        Firebase.database.getReference(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as Map<String, String>

                Firebase.database.getReference("ALL_USERS").child("login-${login}").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value != null) {

                            Firebase.database.getReference("TASK").addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.value == null) {
                                        Firebase.database.getReference("TASK").setValue(
                                            arrayListOf(
                                                mapOf(
                                                    Pair("creater", data["login"]),
                                                    Pair("title", title),
                                                    Pair("message", message),
                                                    Pair("login", login),
                                                    Pair("time", time),
                                                    Pair("status", "Не определен"),
                                                    Pair("time_create", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())),
                                                    Pair("id", Random().nextInt().toString()),
                                                )
                                            )
                                        )
                                    } else if (snapshot.value is ArrayList<*>) {
                                        var array = snapshot.value as ArrayList<Map<Any, Any>>

                                        array.add(
                                            mapOf(
                                                Pair("creater", data["login"] ?: "not found"),
                                                Pair("title", title),
                                                Pair("message", message),
                                                Pair("login", login),
                                                Pair("time", time),
                                                Pair("status", "Не определен"),
                                                Pair("time_create", SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())),
                                                Pair("id", Random().nextInt().toString()),
                                            )
                                        )

                                        Firebase.database.getReference("TASK").setValue(
                                            array
                                        )
                                    }

                                    createLiveData.value = true

                                    showProgress(false)
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    showProgress(false)
                                }
                            })
                        } else {
                            showAlert("Логин пользователя не найден")

                            showProgress(false)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        showProgress(false)
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                showProgress(false)
            }
        })
    }
}