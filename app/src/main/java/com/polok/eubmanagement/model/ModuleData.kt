package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class ModuleData(
    @SerializedName("title") val title: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("key") val key: String? = null
)