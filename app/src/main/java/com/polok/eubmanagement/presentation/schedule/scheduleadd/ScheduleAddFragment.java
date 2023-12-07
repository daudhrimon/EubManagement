package com.polok.eubmanagement.presentation.schedule.scheduleadd;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentScheduleAddBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ScheduleAddFragment extends BaseFragment<FragmentScheduleAddBinding> {
    @Override
    protected FragmentScheduleAddBinding initViewBinding() {
        return FragmentScheduleAddBinding.inflate(getLayoutInflater());
    }
    ScheduleAddViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ScheduleAddViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        viewModel.dayZero = getResources().getStringArray(R.array.day_spinner)[0];
        binding.daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.day = getResources().getStringArray(R.array.day_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        binding.addScheduleButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateScheduleInputAndUploadToFirebase(
                    binding.courseTitle, binding.courseCode, binding.lecturerName, binding.startEndTime, binding.roomNo
            );
        });
    }
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 1) getActivity().onBackPressed();
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
