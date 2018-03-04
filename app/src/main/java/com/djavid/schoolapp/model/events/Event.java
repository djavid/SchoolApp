package com.djavid.schoolapp.model.events;

import com.djavid.schoolapp.rest.Api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andrei Kolomiets
 */
public class Event {
    public Event() {
        Calendar start = getStartDate();
        start.set(Calendar.SECOND, 0);
        setStartDate(start);

        Calendar end = getEndDate();
        end.set(Calendar.SECOND, 0);
        setEndDate(end);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return (id == event.id);
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
