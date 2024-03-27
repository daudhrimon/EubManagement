package com.polok.eubmanagement.presentation.schedule.scheduleupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.ScheduleData
import com.polok.eubmanagement.util.showErrorOnUi

class ScheduleAddViewModel : BaseViewModel() {
    var day: String? = null
    var dayZero: String? = null

    fun validateScheduleInputAndUploadToFirebase(
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
            day!!, courseTitleEt.getText().toString(),
            courseCodeEt.getText().toString(),
            lecturerNameEt.getText().toString(),
            startEndTimeEt.getText().toString(),
            roomNoEt.getText().toString()
        )
    }

    private fun uploadScheduleTOFirebase(
        day: String,
        courseTitle: String,
        courseCode: String,
        lecturerName: String,
        startEndTime: String,
        roomNo: String
    ) {
        fireLoadingEvent(true)
        val pushNoticeRef: DatabaseReference? = FirebaseDataRef.provideScheduleRef()?.push()
        pushNoticeRef?.setValue(
            ScheduleData(day, courseTitle, courseCode, lecturerName, startEndTime, roomNo)
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Class Schedule Added Successfully")
                fireNavigateEvent(0, null)
            } else fireMessageEvent(task.exception!!.localizedMessage)
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScheduleAddViewModel() as T
        }
    }
}