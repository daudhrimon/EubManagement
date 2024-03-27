package com.polok.eubmanagement.presentation.assignment.assignmentlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemAssignmentListBinding
import com.polok.eubmanagement.presentation.assignment.assignmentlist.AssignmentListAdapter.AssignmentViewHolder
import com.polok.eubmanagement.model.AssignmentData

class AssignmentListAdapter(
    private val onClickListener: (AssignmentData?) -> Unit
) : RecyclerView.Adapter<AssignmentViewHolder>() {
    private var assignmentList: List<AssignmentData?>? = null

    fun submitList(assignmentList: List<AssignmentData?>?) {
        this.assignmentList = assignmentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        return AssignmentViewHolder(
            ItemAssignmentListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        holder.bind(assignmentList?.get(position))
    }

    override fun getItemCount(): Int = assignmentList?.size ?: 0

    inner class AssignmentViewHolder(
        private val binding: ItemAssignmentListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(assignmentData: AssignmentData?) {
            binding.assignmentTitle.text = assignmentData?.title ?: ""
            binding.assignmentDetails.text = assignmentData?.details ?: ""
            binding.createdAt.text = String.format("Created At: %s", assignmentData?.createdAt ?: "")

            itemView.setOnClickListener {
                onClickListener(assignmentData)
            }
        }
    }
}
