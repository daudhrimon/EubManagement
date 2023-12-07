package com.polok.eubmanagement.presentation.schedule.schedulelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.polok.eubmanagement.databinding.ItemNoticeListBinding;
import com.polok.eubmanagement.model.NoticeData;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.NoticeViewHolder> {
    private final List<NoticeData> noticeList;
    private final View.OnClickListener onClickListener;
    private int adapterPosition;
    public ScheduleListAdapter(List<NoticeData> noticeList, View.OnClickListener onClickListener) {
        this.noticeList = noticeList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(
                ItemNoticeListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.bind(noticeList.get(position));
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        ItemNoticeListBinding binding;
        public NoticeViewHolder(@NonNull ItemNoticeListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(NoticeData noticeData) {
            adapterPosition = getAdapterPosition();
            binding.noticeTitle.setText(noticeData.getNotNullText(noticeData.getTitle()));
            binding.noticeDetails.setText(noticeData.getNotNullText(noticeData.getDetails()));
            binding.createdAt.setText(noticeData.getNotNullText(noticeData.getCreatedAt()));

            if (onClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onClickListener.onClick(itemView);
                });
            } else binding.getRoot().setClickable(false);
        }
    }

    public int getAdapterPosition() {return adapterPosition;}
}
