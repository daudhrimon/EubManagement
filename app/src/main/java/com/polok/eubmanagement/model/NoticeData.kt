package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class NoticeData(
    @SerializedName("title") var title: String? = null,
    @SerializedName("details") var details: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("key") var key: String? = null
)