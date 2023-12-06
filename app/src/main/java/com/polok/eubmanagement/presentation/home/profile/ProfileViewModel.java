package com.polok.eubmanagement.presentation.home.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.util.SharedPref;

public class ProfileViewModel extends BaseViewModel {
    private final MutableLiveData<UserProfileData> userProfileDataLiveData = new MutableLiveData<>();
    public LiveData<UserProfileData> getUserProfileDataLiveData() {return userProfileDataLiveData;}


    public void fetchUserProfileLiveData() {
        try {
            userProfileDataLiveData.postValue(SharedPref.getUserProfile());
        } catch (Exception e) {
            fireMessageEvent(e.getLocalizedMessage());
        }
    }
}
