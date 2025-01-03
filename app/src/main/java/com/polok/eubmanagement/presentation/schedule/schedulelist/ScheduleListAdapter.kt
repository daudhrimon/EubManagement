package com.polok.eubmanagement.presentation.schedule.schedulelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemScheduleListBinding
import com.polok.eubmanagement.model.ScheduleData
import com.polok.eubmanagement.presentation.schedule.schedulelist.ScheduleListAdapter.ScheduleViewHolder
import com.polok.eubmanagement.util.makeVisible

class ScheduleListAdapter(
    private val onUpdateClickListener: ((ScheduleData?) -> Unit),
) : RecyclerView.Adapter<ScheduleViewHolder>() {
    var isAdmin: Boolean? = null
    private var scheduleList: List<ScheduleData?>? = null

    fun submitList(scheduleList: List<ScheduleData?>?) {
        this.scheduleList = scheduleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        return ScheduleViewHolder(
            ItemScheduleListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(scheduleList?.get(position))
    }

    override fun getItemCount(): Int = scheduleList?.size ?: 0

    inner class ScheduleViewHolder(
        private val binding: ItemScheduleListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(scheduleData: ScheduleData?) {
            binding.day.text = String.format("Day\n%s", scheduleData?.day ?: "")
            binding.courseCode.text = String.format("Course Code\n%s", scheduleData?.courseCode ?: "")
            binding.lecturerName.text = String.format("Lecturer Name\n%s", scheduleData?.lecturerName ?: "")
            binding.startEndTime.text = String.format("Start-End Time\n%s", scheduleData?.startEndTime ?: "")
            binding.roomNo.text = String.format("Room No\n%s", scheduleData?.roomNo ?: "")

            if (isAdmin == true) {
                binding.updateDelete.root.makeVisible()
                binding.updateDelete.updateButton.setOnClickListener {
                    onUpdateClickListener(scheduleData)
                }
            }
        }
    }
}
