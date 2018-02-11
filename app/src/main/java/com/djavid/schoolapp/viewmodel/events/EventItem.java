package com.djavid.schoolapp.viewmodel.events;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.dto.events.Event;

import java.util.Calendar;

/**
 * @author Andrei Kolomiets
 */
class EventItem extends BaseObservable implements View.OnClickListener {
    protected final Event event;

    EventItem(Event event) {
        this.event = event;
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

    @Override
    public void onClick(View view) {

    }
}
