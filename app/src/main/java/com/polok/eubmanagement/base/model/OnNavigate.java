package com.polok.eubmanagement.base.model;

import android.os.Bundle;

public class OnNavigate {
    private int id;
    private Bundle bundle;

    public OnNavigate(int id, Bundle bundle) {
        this.id = id;
        this.bundle = bundle;
    }

    public OnNavigate(int id) {
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
