package com.polok.eubmanagement.presentation.schedule.schedulelist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.model.ScheduleData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScheduleListViewModel extends BaseViewModel {
    private final MutableLiveData<List<ScheduleData>> scheduleLiveData = new MutableLiveData<>();
    public LiveData<List<ScheduleData>> getScheduleLiveData() {return scheduleLiveData;}

    public void fetchScheduleListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideScheduleRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<ScheduleData> noticeList = new ArrayList<>();
                    for (DataSnapshot noticeSnapshot : snapshot.getChildren()) {
                        if (noticeSnapshot.exists()) noticeList.add(noticeSnapshot.getValue(ScheduleData.class));
                    }
                    try {
                        Collections.reverse(noticeList);
                    } catch (Exception ignored) {} finally {
                        scheduleLiveData.postValue(noticeList);
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
}