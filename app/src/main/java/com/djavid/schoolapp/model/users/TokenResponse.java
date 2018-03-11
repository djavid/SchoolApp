package com.djavid.schoolapp.model.users;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Andrei Kolomiets
 */
public class TokenResponse {
    @SerializedName("token")
    @Expose
    public String token;

    @SerializedName("level")
    @Expose
    public int level;

    public Level getLevel() {
        return Level.valueOf(level);
    }

    public void setLevel(Level level) {
        this.level = level.ordinal();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("Token: %s, Level: %d", token, level);
    }
}
