package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class UserProfileData(
    @SerializedName("student_id") val studentId: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
    @SerializedName("blood_group") val bloodGroup: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("user_id") val userId: String? = null,
    @SerializedName("admin") val admin: Boolean? = true
)