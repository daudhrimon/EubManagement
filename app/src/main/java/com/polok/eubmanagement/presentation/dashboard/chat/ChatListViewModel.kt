package com.polok.eubmanagement.presentation.dashboard.chat

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
        FirebaseDbRef.provideChatRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val chatList = mutableListOf<UserProfileData?>()

                        for (dataSnapshot in snapshot.getChildren()) {
                            if (dataSnapshot.exists() && dataSnapshot?.key?.contains(ownUserId.toString()) == true) {

                                fetchClientProfileDataAndAddToChatList(
                                    clientUserId = dataSnapshot?.key?.replace(ownUserId.toString(),""),
                                    chatList = chatList
                                )
                            }
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

    private fun fetchClientProfileDataAndAddToChatList(
        clientUserId: String?,
        chatList: MutableList<UserProfileData?>?
    ) {
        provideStudentRef()?.child(
            clientUserId.toString()
        )?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(profileSnapshot: DataSnapshot) {
                    if (profileSnapshot.exists()) {
                        profileSnapshot.getValue(UserProfileData::class.java)?.also { value ->
                           if (value.userId?.isNotEmpty() == true) {
                               _chatsLiveData.postValue(
                                   chatList?.apply { add(value) }?.reversed()
                               )
                           }
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

    class Factory(
        private val ownUserId: String?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatListViewModel(ownUserId) as T
        }
    }
}
