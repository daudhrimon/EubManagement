package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class UserProfileData(
    @SerializedName("studentId") var studentId: String? = null,
    @SerializedName("fullName") val fullName: String? = null,
    @SerializedName("mobileNumber") val mobileNumber: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("section") val section: String? = null,
    @SerializedName("bloodGroup") val bloodGroup: String? = null,
    @SerializedName("admin") val isAdmin: Boolean? = null
)