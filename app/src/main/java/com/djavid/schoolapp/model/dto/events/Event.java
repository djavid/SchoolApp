package com.djavid.schoolapp.model.dto.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author Andrei Kolomiets
 */
public class Event {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("place")
    @Expose
    public String place;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("created_by")
    @Expose
    public String created_by;

    @SerializedName("participation_groups")
    @Expose
    public String participation_groups;

    @SerializedName("start_date")
    @Expose
    public Date start_date;

    @SerializedName("end_date")
    @Expose
    public Date end_date;

}
