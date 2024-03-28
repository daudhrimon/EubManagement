package com.polok.eubmanagement.presentation.module.modulelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.polok.eubmanagement.databinding.ItemModuleListBinding
import com.polok.eubmanagement.model.ModuleData
import com.polok.eubmanagement.presentation.module.modulelist.ModuleListAdapter.ModuleViewHolder
import com.polok.eubmanagement.util.makeVisible

class ModuleListAdapter(
    private val onClickListener: (ModuleData?) -> Unit,
    private val onUpdateClickListener: (ModuleData?) -> Unit
) : RecyclerView.Adapter<ModuleViewHolder>() {
    var isAdmin: Boolean? = null
    private var moduleList: List<ModuleData?>? = null

    fun submitList(moduleList: List<ModuleData?>?) {
        this.moduleList = moduleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleViewHolder {
        return ModuleViewHolder(
            ItemModuleListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ModuleViewHolder, position: Int) {
        holder.bind(moduleList?.get(position))
    }

    override fun getItemCount(): Int = moduleList?.size ?: 0

    inner class ModuleViewHolder(
        private val binding: ItemModuleListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(moduleData: ModuleData?) {
            binding.moduleTitle.text = moduleData?.title ?: ""
            binding.createdAt.text = String.format("Created At: %s", moduleData?.createdAt ?: "")

            binding.downloadButton.setOnClickListener {
                onClickListener(moduleData)
            }

            if (isAdmin == true) {
                binding.updateDelete.root.makeVisible()
                binding.updateDelete.updateButton.setOnClickListener {
                    onUpdateClickListener(moduleData)
                }
            }
        }
    }
}
