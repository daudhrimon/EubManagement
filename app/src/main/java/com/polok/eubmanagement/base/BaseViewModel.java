package com.polok.eubmanagement.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.polok.eubmanagement.base.event.Event;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Event<String>> messageEvent = new MutableLiveData<>();
    public LiveData<Event<String>> getMessageEvent() {return messageEvent; }

    protected void fireMessageEvent(String message) {
        messageEvent.postValue(new Event<>(message));
    }
}
