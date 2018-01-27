package com.djavid.schoolapp.util;

import android.content.SharedPreferences;

import com.djavid.schoolapp.model.dto.users.Level;


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


    public String getDisplayName() {
        return sharedPreferences.getString("displayName", null);
    }

    public void setDisplayName(String displayName) {
        sharedPreferences
                .edit()
                .putString("displayName", displayName)
                .apply();
    }


    public Level getLevel() {
        return Level.values()[sharedPreferences.getInt("level", Level.None.ordinal())];
    }

    public void setLevel(Level level) {
        sharedPreferences
                .edit()
                .putInt("level", level.ordinal())
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
