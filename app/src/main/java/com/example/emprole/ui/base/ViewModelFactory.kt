package com.example.emprole.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.emprole.data.api.ApiHelper
import com.example.emprole.data.reprository.MainRepositor
import com.example.emprole.ui.main.viewmodel.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(private val apiHelper: ApiHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepositor(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}