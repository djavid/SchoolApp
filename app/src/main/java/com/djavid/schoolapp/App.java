package com.djavid.schoolapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.djavid.schoolapp.core.PresenterProvider;
import com.djavid.schoolapp.util.SavedPreferences;


public class App extends Application {

    private static App appInstance;
    private PresenterProvider presenterProvider;
    private SharedPreferences sharedPreferences;
    private SavedPreferences savedPreferences;
    private static String SHARED_PREFERENCES_CODE = "school_app";


    @Override
    public void onCreate() {
        super.onCreate();

        getPresenterProvider();
        getSharedPreferences();
        getPreferences();

        appInstance = (App) getApplicationContext();
    }

    public static Context getContext() {
        return appInstance.getApplicationContext();
    }

    public static App getAppInstance() {
        return appInstance;
    }

    public PresenterProvider getPresenterProvider() {
        if (presenterProvider == null)
            presenterProvider = new PresenterProvider();

        return presenterProvider;
    }

    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_CODE, MODE_PRIVATE);

        return sharedPreferences;
    }

    public SavedPreferences getPreferences() {
        if (savedPreferences == null)
            savedPreferences = new SavedPreferences(getSharedPreferences());

        return savedPreferences;
    }

}
