package com.polok.eubmanagement.presentation.faculty.facultylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemFacultyListBinding
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.presentation.faculty.facultylist.FacultyListAdapter.FacultyViewHolder
import com.polok.eubmanagement.util.makeVisible

class FacultyListAdapter(
    private val onClickListener: (FacultyData?) -> Unit,
    private val onUpdateClickListener: (FacultyData?) -> Unit
) : RecyclerView.Adapter<FacultyViewHolder>() {
    var isAdmin: Boolean? = null
    private var facultyList: List<FacultyData?>? = null

    fun submitList(facultyList: List<FacultyData?>?) {
        this.facultyList = facultyList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        return FacultyViewHolder(
            ItemFacultyListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {
        holder.bind(facultyList?.get(position))
    }

    override fun getItemCount(): Int = facultyList?.size ?: 0

    inner class FacultyViewHolder(
        private val binding: ItemFacultyListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(facultyData: FacultyData?) {
            binding.moduleTitle.text = facultyData?.name ?: ""
            binding.createdAt.text = String.format("Created At: %s", facultyData?.details ?: "")

            itemView.setOnClickListener {
                onClickListener(facultyData)
            }

            if (isAdmin == true) {
                binding.updateDelete.root.makeVisible()
                binding.updateDelete.updateButton.setOnClickListener {
                    onUpdateClickListener(facultyData)
                }
            }
        }
    }
}
