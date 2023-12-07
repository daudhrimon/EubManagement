package com.polok.eubmanagement.presentation.module.modulelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.polok.eubmanagement.databinding.ItemModuleListBinding;
import com.polok.eubmanagement.model.ModuleData;
import java.util.List;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ModuleViewHolder> {
    private final List<ModuleData> moduleList;
    private final View.OnClickListener onClickListener;
    private String moduleLink;
    public ModuleListAdapter(List<ModuleData> moduleList, View.OnClickListener onClickListener) {
        this.moduleList = moduleList;
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public ModuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ModuleViewHolder(
                ItemModuleListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false)
        );
    }
    @Override
    public void onBindViewHolder(@NonNull ModuleViewHolder holder, int position) {
        holder.bind(moduleList.get(position));
    }
    @Override
    public int getItemCount() {
        return moduleList.size();
    }
    public class ModuleViewHolder extends RecyclerView.ViewHolder {
        ItemModuleListBinding binding;
        public ModuleViewHolder(@NonNull ItemModuleListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ModuleData moduleData) {
            binding.moduleTitle.setText(moduleData.getNotNullText(moduleData.getTitle()));
            binding.createdAt.setText(String.format("Created At: %s", moduleData.getNotNullText(moduleData.getCreatedAt())));

            binding.downloadButton.setOnClickListener(view -> {
                moduleLink = moduleData.getLink();
                onClickListener.onClick(view);
            });
        }
    }

    public String getModuleLink() {return moduleLink;}
}
