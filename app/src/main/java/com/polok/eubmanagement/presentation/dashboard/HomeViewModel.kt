package com.polok.eubmanagement.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.NoticeData
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.util.SharedPref

class HomeViewModel : BaseViewModel() {
    private val _userProfileDataLiveData = MutableLiveData<UserProfileData?>()
    val userProfileDataLiveData: LiveData<UserProfileData?> get() = _userProfileDataLiveData
    private val _noticeLiveData = MutableLiveData<List<NoticeData?>?>()
    val noticeLiveData: LiveData<List<NoticeData?>?> get() = _noticeLiveData

    fun fetchUserProfileLiveData() {
        try {
            _userProfileDataLiveData.postValue(SharedPref.getUserProfile())
        } catch (e: Exception) {
            fireMessageEvent(e.localizedMessage)
        }
    }

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
                            val newList: MutableList<NoticeData?> = ArrayList()
                            var itemCount = 0
                            for (noticeData in noticeList) {
                                if (itemCount < 3) {
                                    newList.add(noticeData)
                                    itemCount++
                                } else break
                            }
                            _noticeLiveData.postValue(newList)
                            fireLoadingEvent(false)
                        }
                    } else {
                        fireLoadingEvent(false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    fireLoadingEvent(false)
                }
            }
        )
    }

    fun attemptSignOutUser(firebaseAuth: FirebaseAuth) {
        fireLoadingEvent(true)
        try {
            firebaseAuth.signOut()
        } catch (exception: Exception) {
            fireMessageEvent(exception.localizedMessage)
        } finally {
            fireNavigateEvent(0, null)
            fireLoadingEvent(false)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel() as T
        }
    }
}