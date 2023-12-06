package com.polok.eubmanagement.presentation.notice.viewnotice;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.polok.eubmanagement.databinding.ListItemNoticeBinding;
import com.polok.eubmanagement.presentation.notice.model.NoticeData;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    private final List<NoticeData> noticeList;
    private final Boolean isClickable;
    public NoticeAdapter(List<NoticeData> noticeList, Boolean isClickable) {
        this.noticeList = noticeList;
        this.isClickable = isClickable;
    }

    @NonNull
    @Override
    public NoticeAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoticeViewHolder(
                ListItemNoticeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.NoticeViewHolder holder, int position) {
        holder.bind(noticeList.get(position));
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        ListItemNoticeBinding binding;
        public NoticeViewHolder(@NonNull ListItemNoticeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(NoticeData noticeData) {
            binding.noticeTitle.setText(noticeData.getNotNullText(noticeData.getTitle()));
            binding.noticeDetails.setText(noticeData.getNotNullText(noticeData.getDetails()));

            if (isClickable) {

            } else binding.getRoot().setClickable(false);
        }
    }
}
