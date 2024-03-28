package com.polok.eubmanagement.presentation.faculty.facultylist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentFacultyListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast
import com.polok.eubmanagement.widget.PrimaryLoader

class FacultyListFragment : BaseFragment<FragmentFacultyListBinding>(
    viewBindingFactory = FragmentFacultyListBinding::inflate
) {
    private val viewModel: FacultyListViewModel by viewModels {
        FacultyListViewModel.Factory()
    }
    private val adapter: FacultyListAdapter by lazy {
        FacultyListAdapter(
            onCallNowClickListener = {
                try {
                    startActivity(
                        Intent().apply {
                            action = Intent.ACTION_DIAL
                            data = Uri.parse("tel:$it")
                        }
                    )
                } catch (e: Exception) {
                    context?.showToast("something went wrong")
                    e.printStackTrace()
                }
            },
            onUpdateClickListener = {
                viewModel.navigateToFacultyUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchFacultyListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().isAdmin
        if (adapter.isAdmin == true) binding.addButton.makeVisible()

        viewModel.facultyLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.moduleRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyAddFragment,null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}