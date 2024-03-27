package com.polok.eubmanagement.presentation.module.moduleupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentModuleAddBinding
import com.polok.eubmanagement.model.ModuleData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ModuleUpdateFragment : BaseFragment<FragmentModuleAddBinding>(
    viewBindingFactory = FragmentModuleAddBinding::inflate
) {
    private val viewModel: ModuleUpdateViewModel by viewModels {
        ModuleUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.moduleTitle.setText(arguments?.getString("title"))
        binding.moduleLink.setText(arguments?.getString("link"))

        binding.addModuleButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateModuleInputsAndUploadToFirebase(
                key = arguments?.getString("key"),
                moduleTitleEt = binding.moduleTitle,
                moduleLinkEt = binding.moduleLink
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun processBundle(moduleData: ModuleData?) : Bundle = Bundle().apply {
            putString("title", moduleData?.title)
            putString("link", moduleData?.link)
            putString("created_at", moduleData?.createdAt)
            putString("key", moduleData?.key)
        }
    }
}