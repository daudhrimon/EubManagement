package com.polok.eubmanagement.model

import com.google.gson.annotations.SerializedName

data class ScheduleData(
    @SerializedName("course_title") val courseTitle: String? = null,
    @SerializedName("course_code") val courseCode: String? = null,
    @SerializedName("lecturer_name") val lecturerName: String? = null,
    @SerializedName("start_end_time") val startEndTime: String? = null,
    @SerializedName("room_no") val roomNo: String? = null,
    @SerializedName("day") var day: String? = null,
    @SerializedName("key") val key: String? = null
)
