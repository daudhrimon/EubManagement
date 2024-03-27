package com.polok.eubmanagement.presentation.notice.noticeupdate

import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideNoticeRef
import com.polok.eubmanagement.model.NoticeData
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.util.showErrorOnUi

class NoticeUpdateViewModel : BaseViewModel() {

    fun validateNoticeInputAndUploadToFirebase(
        key: String?, noticeTitleEt: EditText, noticeDetailsEt: EditText
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
            noticeData = NoticeData(
                title = noticeTitleEt.text.toString(),
                details = noticeDetailsEt.text.toString(),
                createdAt = getCurrentDate(), key = key
            )
        )
    }

    private fun uploadNoticeTOFirebase(noticeData: NoticeData) {
        fireLoadingEvent(true)
        provideNoticeRef()?.child(noticeData.key ?: "")?.setValue(
            noticeData
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                fireMessageEvent("Notice Updated Successfully")
                fireNavigateEvent(0, null)
            } else fireMessageEvent(task.exception!!.localizedMessage)
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoticeUpdateViewModel() as T
        }
    }
}