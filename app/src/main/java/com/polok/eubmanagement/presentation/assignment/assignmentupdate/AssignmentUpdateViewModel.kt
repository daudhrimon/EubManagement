package com.polok.eubmanagement.presentation.assignment.assignmentupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideAssignmentRef
import com.polok.eubmanagement.model.AssignmentData
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.util.showErrorOnUi

class AssignmentUpdateViewModel : BaseViewModel() {

    fun validateAssignmentInputAndUploadToFirebase(
        key: String?, assignmentTitleEt: EditText, assignmentDetailsEt: EditText
    ) {
        if (assignmentTitleEt.text.toString().isEmpty()) {
            assignmentTitleEt.showErrorOnUi("Enter Assignment Title")
            return
        }
        if (assignmentDetailsEt.text.toString().isEmpty()) {
            assignmentDetailsEt.showErrorOnUi("Enter Assignment Details")
            return
        }
        uploadAssignmentTOFirebase(
            assignmentData = AssignmentData(
                title = assignmentTitleEt.text.toString(),
                details = assignmentDetailsEt.text.toString(),
                createdAt = getCurrentDate(), key = key
            )
        )
    }

    private fun uploadAssignmentTOFirebase(assignmentData: AssignmentData) {
        fireLoadingEvent(true)
        provideAssignmentRef()?.child(assignmentData.key ?: "")?.setValue(
            assignmentData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Assignment Updated Successfully")
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
            return AssignmentUpdateViewModel() as T
        }
    }
}