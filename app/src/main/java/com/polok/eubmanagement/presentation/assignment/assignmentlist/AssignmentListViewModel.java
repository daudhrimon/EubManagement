package com.polok.eubmanagement.presentation.assignment.assignmentlist;

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
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.assignment.modal.AssignmentData;
import com.polok.eubmanagement.presentation.notice.model.NoticeData;
import java.util.ArrayList;
import java.util.List;

public class AssignmentListViewModel extends BaseViewModel {
    private final MutableLiveData<List<AssignmentData>> assignmentLiveData = new MutableLiveData<>();
    public LiveData<List<AssignmentData>> getAssignmentLiveData() {return assignmentLiveData;}

    public void fetchAssignmentListFromFirebase() {
        fireLoadingEvent(true);
        FirebaseDataRef.provideAssignmentRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<AssignmentData> assignmentList = new ArrayList<>();
                    for (DataSnapshot assignmentSnapshot : snapshot.getChildren()) {
                        if (assignmentSnapshot.exists()) assignmentList.add(assignmentSnapshot.getValue(AssignmentData.class));
                    }
                    assignmentLiveData.postValue(assignmentList);
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

    public void navigateToViewAssignmentFragment(AssignmentData assignmentData) {
        if (assignmentData != null) {
            Bundle bundle = new Bundle();
            bundle.putString("assignment_data", new Gson().toJson(assignmentData));
            fireNavigateEvent(new OnNavigate(R.id.action_viewAssignmentFragment_to_assignmentViewFragment, bundle));
        } else fireMessageEvent("Something went wrong");
    }
}
