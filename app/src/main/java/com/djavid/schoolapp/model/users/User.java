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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
