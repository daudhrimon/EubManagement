package com.polok.eubmanagement.presentation.faculty.facultyadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideFacultyRef
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.showErrorOnUi

class FacultyAddViewModel : BaseViewModel() {
    var gender: String? = null
    var genderZero: String? = null

    fun validateFacultyInputsAndUploadToFirebase(
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
            facultyName = facultyNameEt.text.toString(),
            facultyDesignation = facultyDesignationEt.text.toString(),
            facultyPhone = facultyPhoneEt.text.toString(),
            facultyGender = gender.toString()
        )
    }

    private fun uploadFacultyTOFirebase(
        facultyName: String, facultyDesignation: String, facultyPhone: String, facultyGender: String
    ) {
        fireLoadingEvent(true)
        val dbPushRef: DatabaseReference? = provideFacultyRef()?.push()
        dbPushRef?.setValue(
            FacultyData(
                name = facultyName,
                details = facultyDesignation,
                phone = facultyPhone,
                gender = facultyGender,
                key = dbPushRef.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Faculty's Info Saved Successfully")
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
            return FacultyAddViewModel() as T
        }
    }
}