package com.polok.eubmanagement.base.event;

import android.os.Bundle;

public class NavigateEvent {
    private int id;
    private Bundle bundle;

    public NavigateEvent(int id, Bundle bundle) {
        this.id = id;
        this.bundle = bundle;
    }

    public NavigateEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
