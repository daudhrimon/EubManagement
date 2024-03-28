package com.polok.eubmanagement.presentation.module.moduleadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideModuleRef
import com.polok.eubmanagement.model.ModuleData
import com.polok.eubmanagement.util.showErrorOnUi

class ModuleAddViewModel : BaseViewModel() {

    fun validateModuleInputsAndUploadToFirebase(
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
        uploadModuleTOFirebase(
            moduleTitleEt.getText().toString(), moduleLinkEt.getText().toString(), createdAt
        )
    }

    private fun uploadModuleTOFirebase(moduleTitle: String, moduleLink: String, createdAt: String) {
        fireLoadingEvent(true)
        val dbPushRef: DatabaseReference? = provideModuleRef()?.push()
        dbPushRef?.setValue(
            ModuleData(
                title = moduleTitle,
                link = moduleLink,
                createdAt = createdAt,
                dbPushRef.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Course Module Submitted Successfully")
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
            return ModuleAddViewModel() as T
        }
    }
}