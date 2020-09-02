package com.example.emprole.data.api

import com.example.emprole.data.model.Data
import com.example.emprole.data.model.EmpModelList
import io.reactivex.Single

interface ApiService {

    fun getEmpList() : Single<EmpModelList>


}