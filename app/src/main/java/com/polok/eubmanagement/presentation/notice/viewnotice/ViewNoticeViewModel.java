package com.polok.eubmanagement.presentation.notice.viewnotice;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.notice.model.NoticeData;
import java.util.ArrayList;
import java.util.List;

public class ViewNoticeViewModel extends BaseViewModel {
    private List<NoticeData> noticeList = new ArrayList<>();
    private final MutableLiveData<List<NoticeData>> noticeLiveData = new MutableLiveData<>();
    public LiveData<List<NoticeData>> getNoticeLiveData() {return noticeLiveData;}

    public void fetchNoticeListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideNoticeRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    noticeList.clear();
                    for (DataSnapshot noticeSnapshot : snapshot.getChildren()) {
                        if (noticeSnapshot.exists()) noticeList.add(noticeSnapshot.getValue(NoticeData.class));
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

    @Override
    protected void onCleared() {
        super.onCleared();
        noticeList = null;
    }
}
