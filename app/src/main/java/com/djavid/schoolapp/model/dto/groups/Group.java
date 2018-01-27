package com.djavid.schoolapp.model.dto.groups;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Andrei Kolomiets
 */
public class Group {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("title")
    @Expose
    private String title;

    public Group() {
    }
}
