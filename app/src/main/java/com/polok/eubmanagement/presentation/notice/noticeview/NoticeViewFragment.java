package com.polok.eubmanagement.presentation.notice.noticeview;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentNoticeViewBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeViewFragment extends BaseFragment<FragmentNoticeViewBinding> {
    @Override
    protected FragmentNoticeViewBinding initViewBinding() {
        return FragmentNoticeViewBinding.inflate(getLayoutInflater());
    }
    @Override
    protected BaseViewModel initViewModel() {return null;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}
