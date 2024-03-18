package com.polok.eubmanagement.presentation.module.modulelist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentModuleListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast
import com.polok.eubmanagement.widget.PrimaryLoader

class ModuleListFragment : BaseFragment<FragmentModuleListBinding>(
    viewBindingFactory = FragmentModuleListBinding::inflate
) {
    private val viewModel: ModuleListViewModel by lazy {
        ViewModelProvider(this)[ModuleListViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    private var moduleListAdapter: ModuleListAdapter? = null

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().isAdmin == true) binding.addModuleButton.makeVisible()

        viewModel.fetchModuleListFromFirebase()

        viewModel.moduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                moduleListAdapter = ModuleListAdapter(it) {
                        try {
                            binding.root.context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW, Uri.parse(moduleListAdapter?.moduleLink)
                                )
                            )
                        } catch (e: Exception) {
                            binding.root.context.showToast(e.localizedMessage)
                        }
                    }
                binding.moduleRecycler.setAdapter(moduleListAdapter)
            }
        }

        binding.addModuleButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_moduleListFragment_to_moduleAddFragment,null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader
}