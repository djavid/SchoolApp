package com.djavid.schoolapp.model.schedule;


public class ScheduleLesson {

    private int id;
    private String place;
    private String start_time;
    private String subject;
    private String teacher;


    public ScheduleLesson(int id, String place, String start_time, String subject, String teacher) {
        this.id = id;
        this.place = place;
        this.start_time = start_time;
        this.subject = subject;
        this.teacher = teacher;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
