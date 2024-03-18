package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class ModuleData(
    @SerializedName("title") var title: String? = null,
    @SerializedName("link") var link: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("key") var key: String? = null
)