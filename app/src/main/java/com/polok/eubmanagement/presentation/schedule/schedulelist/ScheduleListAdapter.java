package com.polok.eubmanagement.presentation.schedule.schedulelist;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.polok.eubmanagement.databinding.ItemScheduleListBinding;
import com.polok.eubmanagement.model.ScheduleData;
import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ScheduleViewHolder> {
    private final List<ScheduleData> scheduleList;
    public ScheduleListAdapter(List<ScheduleData> scheduleList) {this.scheduleList = scheduleList;}

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ScheduleViewHolder(
                ItemScheduleListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(scheduleList.get(position));
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class ScheduleViewHolder extends RecyclerView.ViewHolder {
        ItemScheduleListBinding binding;
        public ScheduleViewHolder(@NonNull ItemScheduleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ScheduleData scheduleData) {
            binding.day.setText(String.format("Day\n%s", scheduleData.getNotNullText(scheduleData.getDay())));
            binding.courseCode.setText(String.format("Course Code\n%s", scheduleData.getNotNullText(scheduleData.getCourseCode())));
            binding.lecturerName.setText(String.format("Lecturer Name\n%s", scheduleData.getNotNullText(scheduleData.getLecturerName())));
            binding.startEndTime.setText(String.format("Start-End Time\n%s", scheduleData.getNotNullText(scheduleData.getStartEndTime())));
            binding.roomNo.setText(String.format("Room No\n%s", scheduleData.getNotNullText(scheduleData.getRoomNo())));
        }
    }
}
