package com.polok.eubmanagement.presentation.schedule.schedulelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.ScheduleData

class ScheduleListViewModel : BaseViewModel() {
    private val _scheduleLiveData = MutableLiveData<List<ScheduleData?>?>()
    val scheduleLiveData: LiveData<List<ScheduleData?>?> get() = _scheduleLiveData

    fun fetchScheduleListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDataRef.provideScheduleRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val noticeList: MutableList<ScheduleData?> = ArrayList()
                        for (noticeSnapshot in snapshot.getChildren()) {
                            if (noticeSnapshot.exists()) noticeList.add(
                                noticeSnapshot.getValue(
                                    ScheduleData::class.java
                                )
                            )
                        }
                        try {
                            noticeList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _scheduleLiveData.postValue(noticeList)
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
}