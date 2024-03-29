package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class ChatTextData(
    @SerializedName("text") val text: String? = null,
    @SerializedName("owner_id") val ownerId: String? = null,
    @SerializedName("key") val key: String? = null
)