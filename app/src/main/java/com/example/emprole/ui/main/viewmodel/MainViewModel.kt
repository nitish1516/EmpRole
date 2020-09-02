package com.example.emprole.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.emprole.data.model.EmpModelList
import com.example.emprole.data.reprository.MainRepositor
import com.example.emprole.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepro: MainRepositor) : ViewModel() {

    private val emps= MutableLiveData<Resource<EmpModelList>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchEmps()
    }

    private fun fetchEmps()
    {
        emps.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepro.getEmps()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ empDetails-> emps.postValue(Resource.sucess(empDetails))
                },{t: Throwable? ->
                    emps.postValue(Resource.error("",null)) })
        )
    }

    override fun onCleared()
    {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getEmps(): LiveData<Resource<EmpModelList>>
    {
        return emps
    }
}