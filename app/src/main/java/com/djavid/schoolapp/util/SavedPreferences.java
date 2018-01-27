package com.djavid.schoolapp.util;

import android.content.SharedPreferences;


public class SavedPreferences {

    private SharedPreferences sharedPreferences;


    public SavedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    public String getToken() {
        return sharedPreferences.getString("token", null);
    }
    public void setToken(String token) {
        sharedPreferences
                .edit()
                .putString("token", token)
                .apply();
    }


    public String getIdentity() {
        return sharedPreferences.getString("identity", null);
    }

    public void setIdentity(String token) {
        sharedPreferences
                .edit()
                .putString("identity", token)
                .apply();
    }
}
