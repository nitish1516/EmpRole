package com.example.emprole.data.reprository

import com.example.emprole.data.api.ApiHelper
import com.example.emprole.data.model.EmpModelList
import io.reactivex.Single

class MainRepositor(val apiHelper:ApiHelper) {

    fun getEmps() :Single<EmpModelList>
    {
        return apiHelper.getEmp()
    }
}