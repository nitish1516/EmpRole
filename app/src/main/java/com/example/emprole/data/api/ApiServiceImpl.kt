package com.example.emprole.data.api

import com.example.emprole.data.model.EmpModelList
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single

class ApiServiceImpl : ApiService {
    override fun getEmpList(): Single<EmpModelList> {
        return Rx2AndroidNetworking.get("https://dummy-research.firebaseio.com/data.json")
            .build()
            .getObjectSingle(EmpModelList::class.java)

    }
}