package com.example.taskmanager.Screen.Register

import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.Base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RegisterViewModel: BaseViewModel() {

    var registerLiveData = MutableLiveData<Boolean>()

    fun register(email: String, userFullName: String, login: String, password: String, repeadPassword: String, job: String) {
        if (password.length < 8) {
            showAlert("Пароль должен быть длинее 8 символов")

            return
        }

        if (password != repeadPassword) {
            showAlert("Пароли не совподают")

            return
        }

        if (email.length < 8 && !email.contains("@gmail.com")) {
            showAlert("Email ввиден не коретно")

            return
        }

        if (userFullName.isEmpty()) {
            showAlert("ФИО пустое")

            return
        }

        if (login.isEmpty()) {
            showAlert("ФИО пустое")

            return
        }

        showProgress(true)

        Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Firebase.database.getReference("ALL_USERS")
                    .child("login-${login}").setValue(
                        mapOf(
                            Pair("email", email),
                            Pair("userFullName", userFullName),
                            Pair("job", job),
                            Pair("login", login),
                            Pair("id", task.result.user?.uid.toString()),
                        )
                    )

                Firebase.database.getReference(task.result.user?.uid.toString()).setValue(
                    mapOf(
                        Pair("email", email),
                        Pair("userFullName", userFullName),
                        Pair("job", job),
                        Pair("login", login),
                    )
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showProgress(false)

                        registerLiveData.value = true
                    } else {
                        showProgress(false)

                        showAlert("Возникла системная ошибка")
                    }
                }
            } else {
                showProgress(false)

                showAlert("Возникла ошибка при регистрации (email ввтден не коретно либо он занят)")
            }
        }
    }
}