package com.polok.eubmanagement.presentation.schedule.scheduleupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideScheduleRef
import com.polok.eubmanagement.model.ScheduleData
import com.polok.eubmanagement.util.showErrorOnUi

class ScheduleUpdateViewModel : BaseViewModel() {
    var day: String? = null
    var dayZero: String? = null

    fun validateScheduleInputAndUploadToFirebase(
        key: String?,
        courseTitleEt: EditText,
        courseCodeEt: EditText,
        lecturerNameEt: EditText,
        startEndTimeEt: EditText,
        roomNoEt: EditText
    ) {
        if (day == null || day!!.isEmpty() || day == dayZero) {
            fireMessageEvent(dayZero)
            return
        }
        if (courseTitleEt.getText().toString().isEmpty()) {
            courseTitleEt.showErrorOnUi("Enter Course Title")
            return
        }
        if (courseCodeEt.getText().toString().isEmpty()) {
            courseCodeEt.showErrorOnUi("Enter Course Code")
            return
        }
        if (lecturerNameEt.getText().toString().isEmpty()) {
            lecturerNameEt.showErrorOnUi("Enter Lecturer Name")
            return
        }
        if (startEndTimeEt.getText().toString().isEmpty()) {
            startEndTimeEt.showErrorOnUi("Enter Start to End Time")
            return
        }
        if (roomNoEt.getText().toString().isEmpty()) {
            roomNoEt.showErrorOnUi("Enter Room Number")
            return
        }
        uploadScheduleTOFirebase(
            scheduleData = ScheduleData(
                courseTitle = courseTitleEt.text.toString(),
                courseCode = courseCodeEt.text.toString(),
                lecturerName = lecturerNameEt.text.toString(),
                startEndTime = startEndTimeEt.text.toString(),
                roomNo = roomNoEt.text.toString(),
                day = day, key = key
            )
        )
    }

    private fun uploadScheduleTOFirebase(
        scheduleData: ScheduleData
    ) {
        fireLoadingEvent(true)
        provideScheduleRef()?.child(scheduleData.key ?: "")?.setValue(
            scheduleData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Class Schedule Updated Successfully")
                fireNavigateEvent(0, null)
            } else fireMessageEvent(task.exception?.localizedMessage)
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScheduleUpdateViewModel() as T
        }
    }
}