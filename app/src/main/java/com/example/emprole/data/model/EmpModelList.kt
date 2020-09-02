package com.example.emprole.data.model


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose



data class EmpModelList(
    @Expose
    @SerializedName("code")
    var code: Int,
    @Expose
    @SerializedName("data")
    var data: List<Data>,
    @Expose
    @SerializedName("message")
    val message: String
)
