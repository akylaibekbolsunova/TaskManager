package com.example.taskmanager.Screen.Login

import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.Base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel: BaseViewModel() {

    var loginLiveData = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        if (password.length < 8) {
            showAlert("Пароль должен быть длинее 8 символов")

            return
        }

        if (email.length < 8 && !email.contains("@gmail.com")) {
            showAlert("Email ввиден не корректно")

            return
        }

        showProgress(true)

        Firebase.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                loginLiveData.value = true

                showProgress(false)
            } else {
                showAlert("Возникла ошибка при входе. Возможно почта или пароль введенные не верно")

                showProgress(false)
            }
        }
    }
}