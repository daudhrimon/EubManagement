package com.polok.eubmanagement.presentation.assignment.assignmentadd

import android.widget.EditText
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideAssignmentRef
import com.polok.eubmanagement.presentation.assignment.modal.AssignmentData
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.util.showErrorOnUi

class AssignmentAddViewModel : BaseViewModel() {

    fun validateAssignmentInputAndUploadToFirebase(
        assignmentTitleEt: EditText, assignmentDetailsEt: EditText
    ) {
        if (assignmentTitleEt.getText().toString().isEmpty()) {
            assignmentTitleEt.showErrorOnUi("Enter Assignment Title")
            return
        }
        if (assignmentDetailsEt.getText().toString().isEmpty()) {
            assignmentDetailsEt.showErrorOnUi("Enter Assignment Details")
            return
        }
        uploadAssignmentTOFirebase(
            assignmentTitleEt.getText().toString(),
            assignmentDetailsEt.getText().toString(),
            getCurrentDate()
        )
    }

    private fun uploadAssignmentTOFirebase(title: String, details: String, date: String) {
        fireLoadingEvent(true)
        val pushAssignmentRef = provideAssignmentRef()!!.push()
        pushAssignmentRef.setValue(
            AssignmentData(title, details, date, pushAssignmentRef.getKey())
        ).addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Assignment Added Successfully")
                fireNavigateEvent(0, null)
            } else {
                fireMessageEvent(task.exception?.localizedMessage)
            }
            fireLoadingEvent(false)
        }
    }
}