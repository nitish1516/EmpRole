package com.example.emprole.data.model

import android.os.Parcel
import android.os.Parcelable

class EmpDetailsPojo(
    val duties: String?,
    val emailAddress: String?,
    val employeeAge: String?,
    val employeeCode: String?,
    val firstName: String?,
    val isTeamLead: Boolean,
    val jobTitleName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val preferredFullName: String? = null,
    val profileImage: String?,
    val region: String?,
    val userId: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(duties)
        parcel.writeString(emailAddress)
        parcel.writeString(employeeAge)
        parcel.writeString(employeeCode)
        parcel.writeString(firstName)
        parcel.writeByte(if (isTeamLead) 1 else 0)
        parcel.writeString(jobTitleName)
        parcel.writeString(lastName)
        parcel.writeString(phoneNumber)
        parcel.writeString(preferredFullName)
        parcel.writeString(profileImage)
        parcel.writeString(region)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmpDetailsPojo> {
        override fun createFromParcel(parcel: Parcel): EmpDetailsPojo {
            return EmpDetailsPojo(parcel)
        }

        override fun newArray(size: Int): Array<EmpDetailsPojo?> {
            return arrayOfNulls(size)
        }
    }
}