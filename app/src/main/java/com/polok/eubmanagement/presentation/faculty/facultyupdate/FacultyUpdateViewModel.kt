package com.polok.eubmanagement.presentation.faculty.facultyupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideFacultyRef
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.showErrorOnUi

class FacultyUpdateViewModel : BaseViewModel() {
    var gender: String? = null
    var genderZero: String? = null

    fun validateFacultyInputsAndUploadToFirebase(
        key: String?,
        facultyNameEt: EditText,
        facultyDesignationEt: EditText,
        facultyPhoneEt: EditText
    ) {
        if (facultyNameEt.text.toString().isEmpty()) {
            facultyNameEt.showErrorOnUi("Enter Faculty's Name")
            return
        }
        if (facultyPhoneEt.text.toString().isEmpty()) {
            facultyPhoneEt.showErrorOnUi("Enter Faculty's Phone")
            return
        }
        if (facultyDesignationEt.text.toString().isEmpty()) {
            facultyDesignationEt.showErrorOnUi("Enter Faculty's Designation")
            return
        }
        if (gender.isNullOrEmpty() || gender == genderZero) {
            fireMessageEvent(genderZero)
            return
        }
        uploadFacultyTOFirebase(
            facultyData = FacultyData(
                name = facultyNameEt.text.toString(),
                details = facultyDesignationEt.text.toString(),
                phone = facultyPhoneEt.text.toString(),
                gender = gender.toString(), key = key
            )
        )
    }

    private fun uploadFacultyTOFirebase(facultyData: FacultyData) {
        fireLoadingEvent(true)
        provideFacultyRef()?.child(facultyData.key ?: "")?.setValue(
            facultyData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Faculty's Info Updated Successfully")
                fireNavigateEvent(0, null)
            } else {
                fireMessageEvent(task.exception?.localizedMessage)
            }
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FacultyUpdateViewModel() as T
        }
    }
}