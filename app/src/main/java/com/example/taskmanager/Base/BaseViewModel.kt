package com.example.taskmanager.Base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {

    var alertLiveData = MutableLiveData<String>()
    var progressLiveData = MutableLiveData<Boolean>()

    fun showAlert(message: String) {
        alertLiveData.value = message
    }

    fun showProgress(show: Boolean) {
        progressLiveData.value = show
    }
}