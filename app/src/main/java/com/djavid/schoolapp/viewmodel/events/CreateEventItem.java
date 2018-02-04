package com.djavid.schoolapp.viewmodel.events;

import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.dto.events.Event;

import java.util.Calendar;

/**
 * @author Andrei Kolomiets
 */
public class CreateEventItem extends EventItem {
    public CreateEventItem() {
        super(new Event());
    }

    @Bindable
    public int getStartDateYear() {
        return getStartDate().get(Calendar.YEAR);
    }

    public void setStartDateYear(int year) {
        Calendar cal = getStartDate();
        cal.set(Calendar.YEAR, year);
        event.setStartDate(cal);
    }

    @Bindable
    public int getStartDateMonth() {
        return getStartDate().get(Calendar.MONTH);
    }

    public void setStartDateMonth(int month) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MONTH, month);
        event.setStartDate(cal);
    }

    @Bindable
    public int getStartDateDay() {
        return getStartDate().get(Calendar.DATE);
    }

    public void setStartDateDay(int day) {
        Calendar cal = getStartDate();
        cal.set(Calendar.DATE, day);
        event.setStartDate(cal);
    }

    @Bindable
    public int getStartDateHour() {
        return getStartDate().get(Calendar.HOUR);
    }

    public void setStartDateHour(int hour) {
        Calendar cal = getStartDate();
        cal.set(Calendar.HOUR, hour);
        event.setStartDate(cal);
    }

    @Bindable
    public int getStartDateMinute() {
        return getStartDate().get(Calendar.MINUTE);
    }

    public void setStartDateMinute(int minute) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MINUTE, minute);
        event.setStartDate(cal);
    }

    @Bindable
    public int getEndDateYear() {
        return getEndDate().get(Calendar.YEAR);
    }

    public void setEndDateYear(int year) {
        Calendar cal = getStartDate();
        cal.set(Calendar.YEAR, year);
        event.setStartDate(cal);
    }

    @Bindable
    public int getEndDateMonth() {
        return getEndDate().get(Calendar.MONTH);
    }

    public void setEndDateMonth(int month) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MONTH, month);
        event.setStartDate(cal);
    }


    @Bindable
    public int getEndDateDay() {
        return getEndDate().get(Calendar.DATE);
    }

    public void setEndDateDay(int day) {
        Calendar cal = getStartDate();
        cal.set(Calendar.DATE, day);
        event.setStartDate(cal);
    }

    @Bindable
    public int getEndDateHour() {
        return getEndDate().get(Calendar.HOUR);
    }

    public void setEndDateHour(int hour) {
        Calendar cal = getStartDate();
        cal.set(Calendar.HOUR, hour);
        event.setStartDate(cal);
    }


    @Bindable
    public int getEndDateMinute() {
        return getEndDate().get(Calendar.MINUTE);
    }

    public void setEndDateMinute(int minute) {
        Calendar cal = getStartDate();
        cal.set(Calendar.MINUTE, minute);
        event.setStartDate(cal);
    }

    @Bindable
    public boolean isValid() {
        return getTitle() != null
                && !getTitle().isEmpty();
    }

    @Override
    public void notifyPropertyChanged(int fieldId) {
        switch (fieldId) {
            case BR.id:
            case BR.title:
                super.notifyPropertyChanged(BR.valid);
        }

        super.notifyPropertyChanged(fieldId);
    }

}
