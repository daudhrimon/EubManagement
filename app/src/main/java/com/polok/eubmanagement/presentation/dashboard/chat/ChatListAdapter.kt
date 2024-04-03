package com.polok.eubmanagement.presentation.dashboard.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemChatListBinding
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.presentation.dashboard.chat.ChatListAdapter.ChatListViewHolder

class ChatListAdapter(
    private val onClickListener: (UserProfileData?) -> Unit
) : RecyclerView.Adapter<ChatListViewHolder>() {
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(chatList: List<UserProfileData?>?) {
        differ.submitList(chatList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        return ChatListViewHolder(
            ItemChatListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

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

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<UserProfileData?>() {
            override fun areItemsTheSame(
                oldItem: UserProfileData, newItem: UserProfileData
            ): Boolean = oldItem.userId == newItem.userId

            override fun areContentsTheSame(
                oldItem: UserProfileData, newItem: UserProfileData
            ): Boolean = oldItem == newItem
        }
    }
}
