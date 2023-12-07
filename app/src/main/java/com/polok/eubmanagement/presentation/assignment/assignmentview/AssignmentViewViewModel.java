package com.polok.eubmanagement.presentation.assignment.assignmentview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.presentation.assignment.modal.AssignmentData;

public class AssignmentViewViewModel extends BaseViewModel {
    private final MutableLiveData<AssignmentData> assignmentLiveData = new MutableLiveData<>();
    public LiveData<AssignmentData> getAssignmentLiveData() {return assignmentLiveData;}

    public void fetchAssignmentFromBundle(String bundle) {
        try {
            assignmentLiveData.postValue(
                    new Gson().fromJson(bundle,AssignmentData.class)
            );
        } catch (Exception e) {
            fireMessageEvent(e.getLocalizedMessage());
        }
    }
}