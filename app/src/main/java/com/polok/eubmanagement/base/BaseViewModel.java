package com.polok.eubmanagement.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.polok.eubmanagement.base.event.Event;
import com.polok.eubmanagement.base.model.OnNavigate;

public class BaseViewModel extends ViewModel {
    private final MutableLiveData<Event<String>> messageEvent = new MutableLiveData<>();
    public LiveData<Event<String>> getMessageEvent() {return messageEvent;}

    public void fireMessageEvent(String message) {
        if (message != null && !message.isEmpty()) messageEvent.postValue(new Event<>(message));
    }

    private final MutableLiveData<Event<Boolean>> loadingEvent = new MutableLiveData<>();
    public LiveData<Event<Boolean>> getLoadingEvent() {return loadingEvent; }

    public void fireLoadingEvent(Boolean isLoading) {loadingEvent.postValue(new Event<>(isLoading));}

    private final MutableLiveData<Event<OnNavigate>> navigateEvent = new MutableLiveData<>();
    public LiveData<Event<OnNavigate>> getNavigateEvent() {return navigateEvent; }

    public void fireNavigateEvent(OnNavigate onNavigate) {navigateEvent.postValue(new Event<>(onNavigate));}
}
