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
    private val adapter: ModuleListAdapter by lazy {
        ModuleListAdapter {
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(it?.link))
                )
            } catch (e: Exception) {
                context.showToast(e.localizedMessage)
            }
        }
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().isAdmin == true) binding.addModuleButton.makeVisible()

        viewModel.fetchModuleListFromFirebase()

        binding.moduleRecycler.adapter = adapter

        viewModel.moduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) adapter.submitList(it)
        }

        binding.addModuleButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_moduleListFragment_to_moduleAddFragment,null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader
}