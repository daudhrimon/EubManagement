package com.polok.eubmanagement.presentation.module.moduleadd

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentModuleAddBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.widget.PrimaryLoader

class ModuleAddFragment : BaseFragment<FragmentModuleAddBinding>(
    viewBindingFactory = FragmentModuleAddBinding::inflate
) {
    private val viewModel: ModuleAddViewModel by lazy {
        ViewModelProvider(this)[ModuleAddViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.addModuleButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateModuleInputsAndUploadToFirebase(
                binding.moduleTitle, binding.moduleLink, getCurrentDate()
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}