package com.polok.eubmanagement.presentation.faculty.facultyupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideModuleRef
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.util.showErrorOnUi

class FacultyUpdateViewModel : BaseViewModel() {

    fun validateFacultyInputsAndUploadToFirebase(
        key: String?, moduleTitleEt: EditText, moduleLinkEt: EditText
    ) {
        if (moduleTitleEt.text.toString().isEmpty()) {
            moduleTitleEt.showErrorOnUi("Enter Course Module Title")
            return
        }
        if (moduleLinkEt.text.toString().isEmpty()) {
            moduleLinkEt.showErrorOnUi("Enter Course Module Link")
            return
        }
        uploadFacultyTOFirebase(
            facultyData = FacultyData(
                name = moduleTitleEt.text.toString(),
                designation = moduleLinkEt.text.toString(),
                phone = getCurrentDate(), key = key
            )
        )
    }

    private fun uploadFacultyTOFirebase(facultyData: FacultyData) {
        fireLoadingEvent(true)
        provideModuleRef()?.child(facultyData.key ?: "")?.setValue(
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