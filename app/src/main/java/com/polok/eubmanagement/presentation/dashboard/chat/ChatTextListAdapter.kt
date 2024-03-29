package com.polok.eubmanagement.presentation.dashboard.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemChatTextListClientBinding
import com.polok.eubmanagement.databinding.ItemChatTextListOwnerBinding
import com.polok.eubmanagement.model.ChatTextData

class ChatTextListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var ownUserId: String? = null
    private var chatTextList: List<ChatTextData?>? = null

    fun submitList(facultyList: List<ChatTextData?>?, ownUserId: String?) {
        this.ownUserId = ownUserId
        this.chatTextList = facultyList
    }

    override fun getItemViewType(position: Int): Int {
        return when (chatTextList?.get(position)?.ownerId) {
            ownUserId ?: "" -> VIEW_TYPE_OWNER
            else -> VIEW_TYPE_CLIENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_OWNER -> {
                ChatTextListOwnerViewHolder(
                    ItemChatTextListOwnerBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> {
                ChatTextListClientViewHolder(
                    ItemChatTextListClientBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ChatTextListOwnerViewHolder -> {
                holder.bind(chatTextList?.get(position))
            }

            is ChatTextListClientViewHolder -> {
                holder.bind(chatTextList?.get(position))
            }
        }
    }

    override fun getItemCount(): Int = chatTextList?.size ?: 0

    inner class ChatTextListOwnerViewHolder(
        private val binding: ItemChatTextListOwnerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chatTextData: ChatTextData?) {
            binding.text.text = chatTextData?.text ?: ""
        }
    }

    inner class ChatTextListClientViewHolder(
        private val binding: ItemChatTextListClientBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(chatTextData: ChatTextData?) {
            binding.text.text = chatTextData?.text ?: ""
        }
    }

    companion object {
        private const val VIEW_TYPE_OWNER = 0
        private const val VIEW_TYPE_CLIENT = 1
    }
}