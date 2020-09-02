package com.example.emprole.data.model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*
import kotlinx.serialization.internal.PrimitiveDescriptor
import org.json.JSONObject

@Serializable
data class Data (
    val duties: Any,
    val emailAddress: String,
    val employeeAge: String,
    val employeeCode: String,
    val firstName: String,
    val isTeamLead: Boolean,
    val jobTitleName: String,
    val lastName: String,
    val phoneNumber: String,
    val preferredFullName: String? = null,
    val profileImage: String,
    val region: String,
    val userId: String
    )





