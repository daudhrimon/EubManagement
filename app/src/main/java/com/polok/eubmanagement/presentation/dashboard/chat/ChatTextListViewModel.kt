package com.polok.eubmanagement.presentation.dashboard.chat

import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef.provideChatRef
import com.polok.eubmanagement.model.ChatTextData

class ChatTextListViewModel(
    val ownUserId: String?
) : BaseViewModel() {
    private val _chatTextLiveData = MutableLiveData<List<ChatTextData?>?>()
    val chatTextLiveData: LiveData<List<ChatTextData?>?> get() = _chatTextLiveData

    fun validateIdsAndFetchChatTextListFromFirebase(clientUserId: String?) {
        fireLoadingEvent(true)
        provideChatRef()?.addListenerForSingleValueEvent(
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

    private fun fetchChatTextListFromFirebase(combinedUserId: String) {
        provideChatRef()?.child(combinedUserId)?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val chatTextList: MutableList<ChatTextData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                chatTextList.add(
                                    moduleSnapshot.getValue(ChatTextData::class.java)
                                )
                            }
                        }
                        _chatTextLiveData.postValue(chatTextList)
                        fireLoadingEvent(false)
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

    fun validateIdsAndUploadToFirebase(
        textInputEt: AppCompatEditText?,
        sendButton: AppCompatImageView?,
        clientUserId: String?
    ) {
        if (textInputEt?.text.toString().isEmpty()
            || ownUserId.isNullOrEmpty()
            || clientUserId.isNullOrEmpty()
        ) return

        sendButton?.isEnabled = false
        provideChatRef()?.addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    when {
                        snapshot.exists() && snapshot?.hasChild(clientUserId + ownUserId) == true -> {
                            uploadTextInputToFirebase(
                                textInputEt = textInputEt,
                                sendButton = sendButton,
                                combinedUserId = clientUserId + ownUserId
                            )
                        }

                        else -> {
                            uploadTextInputToFirebase(
                                textInputEt = textInputEt,
                                sendButton = sendButton,
                                combinedUserId = ownUserId + clientUserId
                            )
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    sendButton?.isEnabled = true
                }
            }
        )
    }

    private fun uploadTextInputToFirebase(
        textInputEt: AppCompatEditText?,
        sendButton: AppCompatImageView?,
        combinedUserId: String
    ) {
        val dbRef = provideChatRef()?.child(combinedUserId)?.push()
        dbRef?.setValue(
            ChatTextData(
                text = textInputEt?.text.toString(),
                ownerId = ownUserId, key = dbRef.key
            )
        )?.addOnCompleteListener { task ->
            if (task.isComplete) {
                textInputEt?.setText("")
            } else {
                fireMessageEvent(task.exception?.localizedMessage)
            }
            sendButton?.isEnabled = true
        }
    }

    class Factory(
        private val ownUserId: String?
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ChatTextListViewModel(ownUserId) as T
        }
    }
}