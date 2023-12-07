package com.polok.eubmanagement.presentation.schedule.scheduleview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.model.NoticeData;

public class ScheduleViewViewModel extends BaseViewModel {
    private final MutableLiveData<NoticeData> noticeLiveData = new MutableLiveData<>();
    public LiveData<NoticeData> getNoticeLiveData() {return noticeLiveData;}

    public void fetchNoticeFromBundle(String bundle) {
        try {
            noticeLiveData.postValue(
                    new Gson().fromJson(bundle,NoticeData.class)
            );
        } catch (Exception e) {
            fireMessageEvent(e.getLocalizedMessage());
        }
    }
}
