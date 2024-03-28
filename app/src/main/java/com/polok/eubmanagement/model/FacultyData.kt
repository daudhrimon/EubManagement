package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class FacultyData(
    @SerializedName("name") val name: String? = null,
    @SerializedName("designation") var designation: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("key") val key: String? = null
)