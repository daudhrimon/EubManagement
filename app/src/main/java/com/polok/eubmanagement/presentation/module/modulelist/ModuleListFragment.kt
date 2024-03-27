package com.polok.eubmanagement.presentation.module.modulelist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
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
    private val viewModel: ModuleListViewModel by viewModels {
        ModuleListViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().isAdmin == true) binding.addModuleButton.makeVisible()

        viewModel.fetchModuleListFromFirebase()

        viewModel.moduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.moduleRecycler.adapter = ModuleListAdapter { moduleData ->
                    try {
                        startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(moduleData?.link))
                        )
                    } catch (e: Exception) {
                        context.showToast(e.localizedMessage)
                    }
                }.apply {
                    submitList(it)
                }
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