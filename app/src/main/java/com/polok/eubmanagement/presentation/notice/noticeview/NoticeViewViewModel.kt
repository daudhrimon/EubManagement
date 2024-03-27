package com.polok.eubmanagement.presentation.notice.noticeview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.model.NoticeData

class NoticeViewViewModel : BaseViewModel() {
    private val _noticeLiveData = MutableLiveData<NoticeData?>()
    val noticeLiveData: LiveData<NoticeData?> get() = _noticeLiveData

    fun fetchNoticeFromBundle(bundle: String?) {
        try {
            _noticeLiveData.postValue(
                Gson().fromJson(bundle, NoticeData::class.java)
            )
        } catch (e: Exception) {
            fireMessageEvent(e.localizedMessage)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NoticeViewViewModel() as T
        }
    }
}