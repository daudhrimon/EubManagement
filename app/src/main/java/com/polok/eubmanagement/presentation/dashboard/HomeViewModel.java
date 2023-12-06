package com.polok.eubmanagement.presentation.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.notice.model.NoticeData;
import com.polok.eubmanagement.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends BaseViewModel {
    private final MutableLiveData<UserProfileData> userProfileDataLiveData = new MutableLiveData<>();
    public LiveData<UserProfileData> getUserProfileDataLiveData() {return userProfileDataLiveData;}
    private final MutableLiveData<List<NoticeData>> noticeLiveData = new MutableLiveData<>();
    public LiveData<List<NoticeData>> getNoticeLiveData() {return noticeLiveData;}

    public void fetchUserProfileLiveData() {
        try {
            userProfileDataLiveData.postValue(SharedPref.getUserProfile());
        } catch (Exception e) {
            fireMessageEvent(e.getLocalizedMessage());
        }
    }

    public void fetchNoticeListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideNoticeRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<NoticeData> noticeList = new ArrayList<>();
                    int itemCount = 0;
                    for (DataSnapshot noticeSnapshot : snapshot.getChildren()) {
                        if (itemCount < 4) {
                            if (noticeSnapshot.exists()) noticeList.add(noticeSnapshot.getValue(NoticeData.class));
                            itemCount ++;
                        } else break;
                    }
                    noticeLiveData.postValue(noticeList);
                    fireLoadingEvent(false);
                } else fireLoadingEvent(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fireMessageEvent(error.getMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
