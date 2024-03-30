package com.polok.eubmanagement.presentation.dashboard.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideStudentRef
import com.polok.eubmanagement.model.UserProfileData

class ChatListViewModel(
    private val ownUserId: String?
) : BaseViewModel() {
    private val _chatsLiveData = MutableLiveData<List<UserProfileData?>?>()
    val chatsLiveData: LiveData<List<UserProfileData?>?> get() = _chatsLiveData

    fun fetchChatListThatContainsMyId() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideChatRef()?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    when {
                        snapshot.exists() && snapshot?.hasChild(clientUserId + ownUserId) == true -> {
                            fetchChatTextListFromFirebase(
                                combinedUserId = clientUserId + ownUserId
                            )
                        }

                        else -> {
                            fetchChatTextListFromFirebase(
                                combinedUserId = ownUserId + clientUserId
                            )
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    fireLoadingEvent(false)
                }
            }
        )
    }

    fun fetchChatListFromFirebase() {
        fireLoadingEvent(true)
        provideStudentRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val chatList: MutableList<UserProfileData?> = ArrayList()
                        for (dataSnapshot in snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                Log.wtf("dataSnapshot", dataSnapshot.key)
                                chatList.add(
                                    dataSnapshot.getValue(UserProfileData::class.java)
                                )
                            }
                        }
                        try {
                            chatList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _chatsLiveData.postValue(chatList)
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

    class Factory(
        private val ownUserId: String?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListViewModel(ownUserId) as T
        }
    }
}