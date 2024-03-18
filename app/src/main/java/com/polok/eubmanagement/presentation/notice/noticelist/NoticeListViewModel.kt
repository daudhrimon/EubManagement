package com.polok.eubmanagement.presentation.notice.noticelist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.NoticeData

class NoticeListViewModel : BaseViewModel() {
    private val _noticeLiveData = MutableLiveData<List<NoticeData?>?>()
    val noticeLiveData: LiveData<List<NoticeData?>?> get() = _noticeLiveData

    fun fetchNoticeListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDataRef.provideNoticeRef()?.addValueEventListener(
            object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val noticeList: MutableList<NoticeData?> = ArrayList()
                    for (noticeSnapshot in snapshot.getChildren()) {
                        if (noticeSnapshot.exists()) noticeList.add(
                            noticeSnapshot.getValue(
                                NoticeData::class.java
                            )
                        )
                    }
                    try {
                        noticeList.reverse()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        _noticeLiveData.postValue(noticeList)
                    }
                    fireLoadingEvent(false)
                } else fireLoadingEvent(false)
            }

            override fun onCancelled(error: DatabaseError) {
                fireMessageEvent(error.message)
                fireLoadingEvent(false)
            }
        }
        )
    }

    fun navigateToViewNoticeFragment(noticeData: NoticeData?) {
        if (noticeData != null) {
            fireNavigateEvent(
                R.id.action_viewNoticeFragment_to_noticeViewFragment,
                Bundle().apply { putString("notice_data", Gson().toJson(noticeData)) }
            )
        } else fireMessageEvent("Something went wrong")
    }
}