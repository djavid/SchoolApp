package com.djavid.schoolapp.viewmodel.events;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.rest.Api;

import java.util.Calendar;

/**
 * @author Andrei Kolomiets
 */
public class EventItem extends BaseObservable {
    public final boolean isEntered;
    public final boolean isCreated;

    protected final Event event;

    public EventItem(Event event, boolean isEntered, boolean isCreated){
        this.event = event;
        this.isEntered = isEntered;
        this.isCreated = isCreated;
    }

    public EventItem(Event event)
    {
        this(event, false, false);
    }

    public EventItem(){
        this(new Event());
    }

    public long getIdLong() {
        return event.id;
    }

    @Bindable
    public String getId() {
        return Long.toString(event.id);
    }

    public void setId(String id) {
        event.id = Long.parseLong(id);
        notifyPropertyChanged(BR.id);
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public String getTitle() {
        return event.title;
    }

    public void setTitle(String title) {
        event.title = title;
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public String getDescription() {
        return event.description;
    }

    public void setDescription(String description) {
        event.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getPlace() {
        return event.place;
    }

    public void setPlace(String place) {
        event.place = place;
        notifyPropertyChanged(BR.place);
    }

    @Bindable
    public String getStartDateString(){
        return Api.LocalizedDateTime(getStartDate());
    }

    @Bindable
    public String getEndDateString(){
        return Api.LocalizedDateTime(getEndDate());
    }

    public Calendar getStartDate() {
        return event.getStartDate();
    }

    public Calendar getEndDate() {
        return event.getEndDate();
    }

    @Bindable
    public String getCreatedBy() {
        return event.created_by;
    }
    @Bindable
    public int getStartDateYear() {
        return getStartDate().get(Calendar.YEAR);
    }

    public void setStartDateYear(int year) {
        Calendar cal = getStartDate();
        cal.set(Calendar.YEAR, year);
        event.setStartDate(cal);
        notifyPropertyChanged(BR.startDateString);
    }

    @Bindable
    public int getStartDateMonth() {
        return getStartDate().get(Calendar.MONTH);
    }

    public void setStartDateMonth(int month) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MONTH, month);
        event.setStartDate(cal);
        notifyPropertyChanged(BR.startDateString);
    }

    @Bindable
    public int getStartDateDay() {
        return getStartDate().get(Calendar.DATE);
    }

    public void setStartDateDay(int day) {
        Calendar cal = getStartDate();
        cal.set(Calendar.DATE, day);
        event.setStartDate(cal);
        notifyPropertyChanged(BR.startDateString);
    }

    @Bindable
    public int getStartDateHour() {
        return getStartDate().get(Calendar.HOUR);
    }

    public void setStartDateHour(int hour) {
        Calendar cal = getStartDate();
        cal.set(Calendar.HOUR, hour);
        event.setStartDate(cal);
        notifyPropertyChanged(BR.startDateString);
    }

    @Bindable
    public int getStartDateMinute() {
        return getStartDate().get(Calendar.MINUTE);
    }

    public void setStartDateMinute(int minute) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MINUTE, minute);
        event.setStartDate(cal);
        notifyPropertyChanged(BR.startDateString);
    }

    @Bindable
    public int getEndDateYear() {
        return getEndDate().get(Calendar.YEAR);
    }

    public void setEndDateYear(int year) {
        Calendar cal = getEndDate();
        cal.set(Calendar.YEAR, year);
        event.setEndDate(cal);
        notifyPropertyChanged(BR.endDateString);
    }

    @Bindable
    public int getEndDateMonth() {
        return getEndDate().get(Calendar.MONTH);
    }

    public void setEndDateMonth(int month) {
        Calendar cal = getEndDate();
        cal.set(Calendar.MONTH, month);
        event.setEndDate(cal);
        notifyPropertyChanged(BR.endDateString);
    }


    @Bindable
    public int getEndDateDay() {
        return getEndDate().get(Calendar.DATE);
    }

    public void setEndDateDay(int day) {
        Calendar cal = getEndDate();
        cal.set(Calendar.DATE, day);
        event.setEndDate(cal);
        notifyPropertyChanged(BR.endDateString);
    }

    @Bindable
    public int getEndDateHour() {
        return getEndDate().get(Calendar.HOUR);
    }

    public void setEndDateHour(int hour) {
        Calendar cal = getEndDate();
        cal.set(Calendar.HOUR, hour);
        event.setEndDate(cal);
        notifyPropertyChanged(BR.endDateString);
    }


    @Bindable
    public int getEndDateMinute() {
        return getEndDate().get(Calendar.MINUTE);
    }

    public void setEndDateMinute(int minute) {
        Calendar cal = getEndDate();
        cal.set(Calendar.MINUTE, minute);
        event.setEndDate(cal);
        notifyPropertyChanged(BR.endDateString);
    }

    @Bindable
    public boolean isValid() {
        return getTitle() != null
                && !getTitle().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventItem eventItem = (EventItem) o;

        return event.equals(eventItem.event);
    }

    @Override
    public int hashCode() {
        return event.hashCode();
    }
}
