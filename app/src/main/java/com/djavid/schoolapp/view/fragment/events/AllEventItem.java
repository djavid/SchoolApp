package com.djavid.schoolapp.view.fragment.events;

import com.djavid.schoolapp.model.dto.events.Event;

import java.util.Date;
import java.util.List;

/**
 * @author Andrei Kolomiets
 */
public class AllEventItem {
    public final long id;
    public final String title;
    public final String place;
    public final String description;
    public final String createdBy;
    //public final List<Long> participationGroups;
    public final String startDate;
    public final String endDate;

    public boolean isMyEvent;

    private final Event event;

    public AllEventItem(Event event, boolean isMyEvent) {
        this.event = event;
        this.isMyEvent = isMyEvent;

        this.id = event.id;
        this.title = event.title;
        this.place = event.place;
        this.description = event.description;
        this.createdBy = event.created_by;
        this.startDate = event.start_date.toString();
        this.endDate = event.end_date.toString();
    }
}
