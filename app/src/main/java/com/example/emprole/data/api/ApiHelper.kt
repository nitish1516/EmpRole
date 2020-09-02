package com.example.emprole.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getEmp() =apiService.getEmpList()
}