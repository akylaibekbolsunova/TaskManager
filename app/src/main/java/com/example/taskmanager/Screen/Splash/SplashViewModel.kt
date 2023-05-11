package com.example.taskmanager.Screen.Splash

import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.Base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel: BaseViewModel() {

    var userStatusObserver = MutableLiveData<Boolean>()

    fun checkUserStatus() {
        userStatusObserver.value = Firebase.auth.currentUser != null
    }
}