package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class FacultyData(
    @SerializedName("name") val name: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("details") var details: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("key") val key: String? = null
)