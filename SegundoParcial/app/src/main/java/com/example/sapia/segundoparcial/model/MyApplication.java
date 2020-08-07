package com.example.sapia.segundoparcial.model;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Sapia on 10/11/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);

    }
}
