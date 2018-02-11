package com.djavid.schoolapp.model.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Andrei Kolomiets
 */
public class User {
    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("level")
    @Expose
    public int level;
}
