package com.polok.eubmanagement.presentation.faculty.facultyadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideFacultyRef
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.showErrorOnUi

class FacultyAddViewModel : BaseViewModel() {

    fun validateFacultyInputsAndUploadToFirebase(
        moduleTitleEt: EditText,
        moduleLinkEt: EditText,
        createdAt: String
    ) {
        if (moduleTitleEt.getText().toString().isEmpty()) {
            moduleTitleEt.showErrorOnUi("Enter Course Module Title")
            return
        }
        if (moduleLinkEt.getText().toString().isEmpty()) {
            moduleLinkEt.showErrorOnUi("Enter Course Module Link")
            return
        }
        uploadFacultyTOFirebase(
            moduleTitleEt.getText().toString(), moduleLinkEt.getText().toString(), createdAt
        )
    }

    private fun uploadFacultyTOFirebase(
        facultyName: String, facultyDesignation: String, facultyPhone: String
    ) {
        fireLoadingEvent(true)
        val dbPushRef: DatabaseReference? = provideFacultyRef()?.push()
        dbPushRef?.setValue(
            FacultyData(
                name = facultyName,
                designation = facultyDesignation,
                phone = facultyPhone,
                dbPushRef.key
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