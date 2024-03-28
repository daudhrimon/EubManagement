package com.polok.eubmanagement.presentation.schedule.schedulelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.ScheduleData
import com.polok.eubmanagement.presentation.schedule.scheduleupdate.ScheduleUpdateFragment

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
                        try {
                            for (noticeSnapshot in snapshot.getChildren()) {
                                if (noticeSnapshot.exists()) noticeList.add(
                                    noticeSnapshot.getValue(ScheduleData::class.java)
                                )
                            }
                            noticeList.reverse()
                        } catch (e: Exception) {
                            fireMessageEvent("Something went wrong")
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

    fun navigateToScheduleUpdateFragment(scheduleData: ScheduleData?) {
        if (scheduleData != null) {
            fireNavigateEvent(
                R.id.action_scheduleListFragment_to_scheduleUpdateFragment,
                ScheduleUpdateFragment.generateBundle(scheduleData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScheduleListViewModel() as T
        }
    }
}