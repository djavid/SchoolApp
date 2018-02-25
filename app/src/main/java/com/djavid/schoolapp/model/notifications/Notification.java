package com.djavid.schoolapp.model.notifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class Notification implements Serializable {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("text")
    @Expose
    public String text = "";

    @SerializedName("frequency")
    @Expose
    public String frequency = "0 0 * * *";

    @SerializedName("send_once")
    @Expose
    public boolean noRepeat = true;

    @SerializedName("groups")
    @Expose
    public List<Long> groups = new LinkedList<>();

    @SerializedName("created_by")
    @Expose
    public String author = "";
}
