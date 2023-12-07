package com.polok.eubmanagement.base;

import android.os.Bundle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.polok.eubmanagement.base.event.Event;
import com.polok.eubmanagement.base.model.OnNavigate;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Event<String>> messageEvent = new MutableLiveData<>();
    public LiveData<Event<String>> getMessageEvent() {return messageEvent;}

    private final MutableLiveData<Event<Boolean>> loadingEvent = new MutableLiveData<>();
    public LiveData<Event<Boolean>> getLoadingEvent() {return loadingEvent; }

    private final MutableLiveData<Event<OnNavigate>> navigateEvent = new MutableLiveData<>();
    public LiveData<Event<OnNavigate>> getNavigateEvent() {return navigateEvent; }

    protected void fireMessageEvent(String message) {
        if (message != null && !message.isEmpty()) messageEvent.postValue(new Event<>(message));
    }

    protected void fireLoadingEvent(Boolean isLoading) {
        loadingEvent.postValue(new Event<>(isLoading));
    }

    public void fireNavigateEvent(int id, Bundle bundle) {
        navigateEvent.postValue(new Event<>(new OnNavigate(id, bundle)));
    }
}
