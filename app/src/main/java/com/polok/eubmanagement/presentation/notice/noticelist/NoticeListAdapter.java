package com.polok.eubmanagement.presentation.notice.noticelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.polok.eubmanagement.databinding.ItemNoticeListBinding;
import com.polok.eubmanagement.model.NoticeData;
import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.NoticeViewHolder> {
    private final List<NoticeData> noticeList;
    private final View.OnClickListener onClickListener;
    private int adapterPosition;
    public NoticeListAdapter(List<NoticeData> noticeList, View.OnClickListener onClickListener) {
        this.noticeList = noticeList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public NoticeListAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(
                ItemNoticeListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeListAdapter.NoticeViewHolder holder, int position) {
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
            binding.noticeTitle.setText(noticeData.getNotNullText(noticeData.getTitle()));
            binding.noticeDetails.setText(noticeData.getNotNullText(noticeData.getDetails()));
            binding.createdAt.setText(String.format("Created At: %s", noticeData.getNotNullText(noticeData.getCreatedAt())));

            if (onClickListener != null) {
                itemView.setOnClickListener(v -> {
                    adapterPosition = getAdapterPosition();
                    onClickListener.onClick(itemView);
                });
            } else binding.getRoot().setClickable(false);
        }
    }

    public int getAdapterPosition() {return adapterPosition;}
}
