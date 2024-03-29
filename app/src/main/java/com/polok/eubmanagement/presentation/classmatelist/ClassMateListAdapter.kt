package com.polok.eubmanagement.presentation.classmatelist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemClassMateListBinding
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.presentation.classmatelist.ClassMateListAdapter.ClassMateViewHolder
import com.polok.eubmanagement.util.showToast

class ClassMateListAdapter(
    private val onClickListener: (UserProfileData?) -> Unit,
    private val onCallNowClickListener: (String?) -> Unit
) : RecyclerView.Adapter<ClassMateViewHolder>() {
    private var classMateList: List<UserProfileData?>? = null

    fun submitList(facultyList: List<UserProfileData?>?) {
        this.classMateList = facultyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassMateViewHolder {
        return ClassMateViewHolder(
            ItemClassMateListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ClassMateViewHolder, position: Int) {
        holder.bind(classMateList?.get(position))
    }

    override fun getItemCount(): Int = classMateList?.size ?: 0

    inner class ClassMateViewHolder(
        private val binding: ItemClassMateListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userProfileData: UserProfileData?) {
            binding.mateName.text = userProfileData?.name ?: ""
            binding.matePhone.text = userProfileData?.phone ?: ""
            binding.mateDetails.text = userProfileData?.bloodGroup ?: ""

            itemView.setOnClickListener {
                onClickListener(userProfileData)
            }

            binding.callNowButton.setOnClickListener {
                onCallNowClickListener(userProfileData?.phone)
            }

            binding.matePhone.setOnClickListener {
                (itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?)?.apply {
                    setPrimaryClip(ClipData.newPlainText(userProfileData?.name, userProfileData?.phone))
                    itemView.context.showToast("Phone number copied")
                }
            }
        }
    }
}