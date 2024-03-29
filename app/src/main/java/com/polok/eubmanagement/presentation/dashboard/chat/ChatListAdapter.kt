package com.polok.eubmanagement.presentation.dashboard.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemChatListBinding
import com.polok.eubmanagement.model.FacultyData

class ChatListAdapter (
    private val onClickListener: (String?) -> Unit,
) : RecyclerView.Adapter<ChatListAdapter.FacultyViewHolder>() {
    var isAdmin: Boolean? = null
    private var facultyList: List<FacultyData?>? = null

    fun submitList(facultyList: List<FacultyData?>?) {
        this.facultyList = facultyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        return FacultyViewHolder(
            ItemChatListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        holder.bind(facultyList?.get(position))
    }

    override fun getItemCount(): Int = facultyList?.size ?: 0

    inner class FacultyViewHolder(
        private val binding: ItemChatListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(facultyData: FacultyData?) {
            binding.name.text = facultyData?.name ?: ""
            binding.text.text = facultyData?.phone ?: ""

            itemView.setOnClickListener {
                onClickListener("")
            }
        }
    }
}
