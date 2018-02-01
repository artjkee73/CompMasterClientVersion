package com.androiddev.artemqa.compmasterclientversion;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by artemqa on 30.01.2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        Realm.init(this);
        super.onCreate();
    }
}
