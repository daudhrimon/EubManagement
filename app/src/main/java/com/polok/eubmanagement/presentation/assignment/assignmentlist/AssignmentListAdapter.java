package com.polok.eubmanagement.presentation.assignment.assignmentlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.polok.eubmanagement.databinding.ItemAssignmentListBinding;
import com.polok.eubmanagement.presentation.assignment.modal.AssignmentData;
import java.util.List;

public class AssignmentListAdapter extends RecyclerView.Adapter<AssignmentListAdapter.AssignmentViewHolder> {
    private final List<AssignmentData> assignmentList;
    private final View.OnClickListener onClickListener;
    private int adapterPosition;
    public AssignmentListAdapter(List<AssignmentData> assignmentList, View.OnClickListener onClickListener) {
        this.assignmentList = assignmentList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AssignmentListAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssignmentViewHolder(
                ItemAssignmentListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentListAdapter.AssignmentViewHolder holder, int position) {
        holder.bind(assignmentList.get(position));
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        ItemAssignmentListBinding binding;
        public AssignmentViewHolder(@NonNull ItemAssignmentListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(AssignmentData assignmentData) {
            adapterPosition = getAdapterPosition();
            binding.assignmentTitle.setText(assignmentData.getNotNullText(assignmentData.getTitle()));
            binding.assignmentDetails.setText(assignmentData.getNotNullText(assignmentData.getDetails()));
            binding.createdAt.setText(String.format("Created At: %s", assignmentData.getNotNullText(assignmentData.getCreatedAt())));

            if (onClickListener != null) {
                itemView.setOnClickListener(v -> {
                    onClickListener.onClick(itemView);
                });
            } else binding.getRoot().setClickable(false);
        }
    }

    public int getAdapterPosition() {return adapterPosition;}
}
