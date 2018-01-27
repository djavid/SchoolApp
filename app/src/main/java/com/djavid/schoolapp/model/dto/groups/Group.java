package com.djavid.schoolapp.model.dto.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Andrei Kolomiets
 */
public class Group {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("title")
    @Expose
    public String title;
}
