package com.djavid.schoolapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.djavid.schoolapp.core.PresenterProvider;
import com.djavid.schoolapp.model.Api;
import com.djavid.schoolapp.model.dto.users.Level;
import com.djavid.schoolapp.util.SavedPreferences;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class App extends Application {

    private static App appInstance;
    private PresenterProvider presenterProvider;
    private SharedPreferences sharedPreferences;
    private SavedPreferences savedPreferences;
    private Api api;
    private static String SHARED_PREFERENCES_CODE = "school_app";


    @Override
    public void onCreate() {
        super.onCreate();

        getPresenterProvider();
        getSharedPreferences();
        getPreferences();
        getApi();

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

    public boolean isTeacher() {
        return getPreferences().getLevel().ordinal() > Level.Student.ordinal();
    }

    public Api getApi() {
        if (api == null)
            api = buildApi();
        return api;
    }

    private Api buildApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(Api.class);
    }
}
