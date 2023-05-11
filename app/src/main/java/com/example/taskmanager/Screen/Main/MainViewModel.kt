package com.example.taskmanager.Screen.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.taskmanager.Base.BaseViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlin.collections.ArrayList

class MainViewModel: BaseViewModel() {

    var userNameLiveData = MutableLiveData<String>()

    var tasksNameLiveData = MutableLiveData<ArrayList<Map<String, String>>>()


    fun getData() {
        showProgress(true)

        Firebase.database.getReference(Firebase.auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value as Map<String, String>

                userNameLiveData.value = data["userFullName"]

                showProgress(false)
            }

            override fun onCancelled(error: DatabaseError) {
                showProgress(false)
            }
        })

        Firebase.database.getReference("TASK").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    val tasks = snapshot.value as ArrayList<Map<String, String>>

                    tasksNameLiveData.value = tasks
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}