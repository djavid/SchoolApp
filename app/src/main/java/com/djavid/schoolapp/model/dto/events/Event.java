package com.djavid.schoolapp.model.dto.events;

import com.djavid.schoolapp.model.Api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andrei Kolomiets
 */
public class Event {
    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("title")
    @Expose
    public String title = "";

    @SerializedName("place")
    @Expose
    public String place = "";

    @SerializedName("description")
    @Expose
    public String description = "";

    @SerializedName("created_by")
    @Expose
    public String created_by = "";

    @SerializedName("participation_groups")
    @Expose
    public List<Long> participation_groups = new LinkedList<>();

    @SerializedName("start_date")
    @Expose
    private String start_date = Api.Date(Calendar.getInstance());

    public Calendar getStartDate() {
        return Api.ParseDate(start_date);
    }

    public void setStartDate(Calendar calendar) {
        start_date = Api.Date(calendar);
    }

    @SerializedName("end_date")
    @Expose
    private String end_date = Api.Date(Calendar.getInstance());

    public Calendar getEndDate() {
        return Api.ParseDate(end_date);
    }

    public void setEndDate(Calendar calendar) {
        end_date = Api.Date(calendar);
    }

}
