package com.djavid.schoolapp.view.fragment.events;

import com.djavid.schoolapp.model.dto.events.Event;

import java.util.Date;
import java.util.List;

/**
 * @author Andrei Kolomiets
 */
public class MyEventItem {
    public final long id;
    public final String title;
    public final String place;
    public final String description;
    public final String createdBy;
    //public final List<Long> participationGroups;
    public final String startDate;
    public final String endDate;

    private final Event event;

    public MyEventItem(Event event) {
        this.event = event;

        this.id = event.id;
        this.title = event.title;
        this.place = event.place;
        this.description = event.description;
        this.createdBy = event.created_by;
        this.startDate = event.start_date.toString();
        this.endDate = event.end_date.toString();
    }
}
