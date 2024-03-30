package com.polok.eubmanagement.presentation.dashboard.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemChatListBinding
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.presentation.dashboard.chat.ChatListAdapter.ChatListViewHolder

class ChatListAdapter(
    private val onClickListener: (UserProfileData?) -> Unit
) : RecyclerView.Adapter<ChatListViewHolder>() {
    private var chatList: List<UserProfileData?>? = null

    fun submitList(facultyList: List<UserProfileData?>?) {
        this.chatList = facultyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(
            ItemChatListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(chatList?.get(position))
    }

    override fun getItemCount(): Int = chatList?.size ?: 0

    inner class ChatListViewHolder(
        private val binding: ItemChatListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userProfileData: UserProfileData?) {
            binding.name.text = userProfileData?.name ?: ""

            itemView.setOnClickListener {
                onClickListener(userProfileData)
            }
        }
    }
}
