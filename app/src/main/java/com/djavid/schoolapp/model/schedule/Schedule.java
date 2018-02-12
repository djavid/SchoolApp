package com.djavid.schoolapp.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Schedule {

    @SerializedName("1")
    @Expose
    public List<ScheduleLesson> monday;
    @SerializedName("2")
    @Expose
    public List<ScheduleLesson> tuesday;
    @SerializedName("3")
    @Expose
    public List<ScheduleLesson> wednesday;
    @SerializedName("4")
    @Expose
    public List<ScheduleLesson> thursday;
    @SerializedName("5")
    @Expose
    public List<ScheduleLesson> friday;
    @SerializedName("6")
    @Expose
    public List<ScheduleLesson> saturday;
    @SerializedName("7")
    @Expose
    public List<ScheduleLesson> sunday;
}
