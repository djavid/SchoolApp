package com.djavid.schoolapp.model.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class Notification {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("text")
    @Expose
    public String text;

    @SerializedName("frequency")
    @Expose
    public String frequency;

    @SerializedName("send_once")
    @Expose
    public boolean noRepeat;

    @SerializedName("groups")
    @Expose
    public List<Long> groups;

    @SerializedName("created_by")
    @Expose
    public String author;
}
