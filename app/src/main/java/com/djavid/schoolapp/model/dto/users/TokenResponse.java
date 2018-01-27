package com.djavid.schoolapp.model.dto.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Andrei Kolomiets
 */
public class TokenResponse {
    @SerializedName("token")
    @Expose
    public String token;
}
