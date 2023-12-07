package com.polok.eubmanagement.presentation.notice.noticelist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.NoticeData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoticeListViewModel extends BaseViewModel {
    private final MutableLiveData<List<NoticeData>> noticeLiveData = new MutableLiveData<>();
    public LiveData<List<NoticeData>> getNoticeLiveData() {return noticeLiveData;}

    public void fetchNoticeListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideNoticeRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<NoticeData> noticeList = new ArrayList<>();
                    for (DataSnapshot noticeSnapshot : snapshot.getChildren()) {
                        if (noticeSnapshot.exists()) noticeList.add(noticeSnapshot.getValue(NoticeData.class));
                    }
                    try {
                        Collections.reverse(noticeList);
                    } catch (Exception ignored) {} finally {
                        noticeLiveData.postValue(noticeList);
                    }
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

    public void navigateToViewNoticeFragment(NoticeData noticeData) {
        if (noticeData != null) {
            Bundle bundle = new Bundle();
            bundle.putString("notice_data", new Gson().toJson(noticeData));
            fireNavigateEvent(R.id.action_viewNoticeFragment_to_noticeViewFragment, bundle);
        } else fireMessageEvent("Something went wrong");
    }
}
