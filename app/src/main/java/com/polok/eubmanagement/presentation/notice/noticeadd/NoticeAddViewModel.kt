package com.polok.eubmanagement.presentation.notice.noticeadd

import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
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
        val pushNoticeRef: DatabaseReference? = FirebaseDataRef.provideNoticeRef()?.push()
        pushNoticeRef?.setValue(
            NoticeData(title, details, date, pushNoticeRef.getKey().toString())
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Notice Added Successfully")
                fireNavigateEvent(0, null)
            } else fireMessageEvent(task.exception!!.localizedMessage)
            fireLoadingEvent(false)
        }
    }
}