package com.polok.eubmanagement.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;
import com.polok.eubmanagement.base.event.Event;
import com.polok.eubmanagement.widget.PrimaryLoader;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding();

        initViewModel();

        initOnCreateView(savedInstanceState);

        if (initViewModel() != null) {
            initViewModel().getMessageEvent().observe(getViewLifecycleOwner(), new Event.EventObserver<>(notHandledMessage -> {
                if (notHandledMessage != null && !notHandledMessage.isEmpty()) {
                    Toast.makeText(getContext(), notHandledMessage, Toast.LENGTH_SHORT).show();
                }
            }));
            initViewModel().getLoadingEvent().observe(getViewLifecycleOwner(), new Event.EventObserver<>(isLoading -> {
                if (isLoading != null && initPrimaryLoader() != null) {
                    if (isLoading) initPrimaryLoader().setVisibility(View.VISIBLE);
                    else initPrimaryLoader().setVisibility(View.GONE);
                }
            }));
        }

        return binding.getRoot();
    }

    protected abstract VB initViewBinding();

    protected abstract BaseViewModel initViewModel();

    protected abstract void initOnCreateView(Bundle savedInstanceState);

    protected abstract PrimaryLoader initPrimaryLoader();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
