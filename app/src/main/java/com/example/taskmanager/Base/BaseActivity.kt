package com.example.taskmanager.Base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

open class BaseActivity<B : ViewBinding, VM: BaseViewModel>(
    val bindingFactory: (LayoutInflater) -> B, 
    val type: Class<VM>
) : AppCompatActivity() {

    lateinit var binding: B
    
    val viewModel: VM by lazy {
        ViewModelProvider(this)[type]
    }

    val progress: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)

        setupBaseObservers()

        setupObservers()
        setupView()
    }

    private fun setupBaseObservers() {
        viewModel.alertLiveData.observe(this) {
            showAlert(it)
        }

        viewModel.progressLiveData.observe(this) {
            if (it) {
                progress.setTitle("Загрузка...")
                progress.setMessage("Идет загрузка/отправка данных")

                progress.show()
            } else {
                progress.dismiss()
            }
        }
    }

    fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Внимание")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, id ->
                dialog.dismiss()
            }.show()
    }

    open fun setupObservers() {
        Log.d("setupObservers", "action")
    }

    open fun setupView() {
        Log.d("setupView", "action")
    }
}