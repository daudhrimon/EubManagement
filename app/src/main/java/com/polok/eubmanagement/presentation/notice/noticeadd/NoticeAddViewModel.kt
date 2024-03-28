package com.polok.eubmanagement.presentation.notice.noticeadd

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideNoticeRef
import com.polok.eubmanagement.model.NoticeData
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.util.showErrorOnUi

class NoticeAddViewModel : BaseViewModel() {

    fun validateNoticeInputAndUploadToFirebase(
        noticeTitleEt: EditText, noticeDetailsEt: EditText
    ) {
        if (noticeTitleEt.getText().toString().isEmpty()) {
            noticeTitleEt.showErrorOnUi("Enter Notice Title")
            return
        }
        if (noticeDetailsEt.getText().toString().isEmpty()) {
            noticeDetailsEt.showErrorOnUi("Enter Notice Details")
            return
        }
        uploadNoticeTOFirebase(
            noticeTitleEt.getText().toString(),
            noticeDetailsEt.getText().toString(),
            getCurrentDate()
        )
    }

    private fun uploadNoticeTOFirebase(title: String, details: String, date: String) {
        fireLoadingEvent(true)
        val dbPushRef: DatabaseReference? = provideNoticeRef()?.push()
        dbPushRef?.setValue(
            NoticeData(
                title = title,
                details = details,
                createdAt = date,
                key = dbPushRef.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Notice Submitted Successfully")
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
            return NoticeAddViewModel() as T
        }
    }
}