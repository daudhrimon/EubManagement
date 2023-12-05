package com.polok.eubmanagement.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polok.eubmanagement.base.event.Event;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Event<String>> messageEvent = new MutableLiveData<>();
    public LiveData<Event<String>> getMessageEvent() {return messageEvent;}

    public void fireMessageEvent(String message) {
        if (message != null && !message.isEmpty()) messageEvent.postValue(new Event<>(message));
    }

    private final MutableLiveData<Event<Boolean>> loadingEvent = new MutableLiveData<>();
    public LiveData<Event<Boolean>> getLoadingEvent() {return loadingEvent; }

    public void fireLoadingEvent(Boolean isLoading) {loadingEvent.postValue(new Event<>(isLoading));}
}
