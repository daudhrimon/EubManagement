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
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideChatRef

class ChatTextViewModel : BaseViewModel() {
    private val _chatTextLiveData = MutableLiveData<List<String?>?>()
    val chatTextLiveData: LiveData<List<String?>?> get() = _chatTextLiveData

    fun fetchFacultyListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideChatRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val facultyList: MutableList<String?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                facultyList.add(
                                    moduleSnapshot.getValue(String::class.java)
                                )
                            }
                        }
                        try {
                            facultyList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _chatTextLiveData.postValue(facultyList)
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

    fun validateTextInputAndUploadToFirebase(
        textInput: String?,
        ownUserId: String?,
        receiverUserId: String?
    ) {
        provideChatRef()
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatTextViewModel() as T
        }
    }
}