package com.djavid.schoolapp.model.event_comments;

import com.djavid.schoolapp.rest.Api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class EventComment {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("text")
    @Expose
    public String text = "";

    @SerializedName("created_by")
    @Expose
    public String author = "";

    @SerializedName("created")
    @Expose
    public String timeStamp = Api.Date(Calendar.getInstance());

    public Calendar getTimeStamp() {
        return Api.ParseDate(timeStamp);
    }

    public void setTimeStamp(Calendar timeStamp) {
        this.timeStamp = Api.Date(timeStamp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventComment eventComment = (EventComment) o;

        return (id == eventComment.id);
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
